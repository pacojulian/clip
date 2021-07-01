package com.paco.clip.app.services;

import com.paco.clip.domain.builder.TransactionBuilder;
import com.paco.clip.domain.model.Disbursement;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.domain.model.User;
import com.paco.clip.domain.repository.DisbursementRepository;
import com.paco.clip.domain.repository.TransactionRepository;
import com.paco.clip.domain.repository.UserRepository;
import com.paco.clip.representation.bo.TransactionBo;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ClipServicesImpl implements ClipServices {

    //Todo exception handler
    //Todo add validation

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionBuilder transactionBuilder;

    private final DisbursementRepository disbursementRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(ClipServicesImpl.class);

    @Override
    public ClipResponse makeTransaction(MakeTransactionRequest request) {
        LOGGER.info("Init transaction");
        ClipResponse response = new ClipResponse();
        try {
            User user = userRepository.findByClipId(request.getClipUser());
            if (user == null) {
                LOGGER.error("Usuario no encontrado");
                throw new RuntimeException();
            }
            LOGGER.info("User Exists with id: {}", request.getClipUser());
            if (!debtFromCard(request.getCard(), request.getAmount())) throw new RuntimeException();
            LOGGER.info("Saving transaction");
            transactionRepository.save(transactionBuilder.buildTransactionObject(request));
            response.setMessage("Operacion guardada correctamente");
            response.setStatusCode(200);
        } catch (Exception e) {
            LOGGER.error("There was an error making a transaction caused by: {}", e.getMessage());
            response.setMessage("Ocurrio un problema al procesar la solitud.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public List<Transaction> getTransactionsByUser(String user) {
        LOGGER.info("Get transactions for user {}", user);
        List<Transaction> response = transactionRepository.findAllByClipUser(user);
        if (response == null || response.isEmpty()) {
            LOGGER.error("There are no transactions for that user");
            throw new RuntimeException();
        }
        return response;
    }

    @Transactional
    @Override
    public List<DisbursementResponse> makeDisbursement(String user) {
        List<DisbursementResponse> response = new ArrayList<>();
        List<Transaction> transactionList = transactionRepository.findAllByClipUserAndIsDisbursementFalseOrderByClient(user);
        if (transactionList.isEmpty()) throw new RuntimeException();
        Map<String, List<TransactionBo>> transactionsGroup = groupByTransaction(transactionList);
        transactionsGroup.forEach((k, v) -> {
            List<Double> transactionsAmount = new ArrayList<>();
            DisbursementResponse disResponse = new DisbursementResponse();
            Double total = v.get(v.size() - 1).getTotal();
            Disbursement disbursement = transactionBuilder.buildDisbursement(user, total);
            disbursementRepository.save(disbursement);
            v.forEach(transactionBo -> {
                updateTransaction(transactionBo.getTransactionId(), disbursement.getDisbursementId());
                transactionsAmount.add(transactionBo.getAmount());
            });
            disResponse.setClient(k);
            disResponse.setClipUser(user);
            disResponse.setTotal(total);
            disResponse.setTransactions(transactionsAmount);
            response.add(disResponse);
        });
        LOGGER.info("Finished disbursement");
        return response;
    }

    @Override
    public DisbursementResponse getDisbursementByUSer(String user) {
        return null;
    }

    private Map<String, List<TransactionBo>> groupByTransaction(List<Transaction> transactionList) {
        Map<String, List<TransactionBo>> transactionsGroup = new HashMap<>();
        for (Transaction t : transactionList) {
            if (transactionsGroup.get(t.getClient()) != null) {
                List<TransactionBo> listAmounts = transactionsGroup.get(t.getClient());
                TransactionBo bo = new TransactionBo();
                bo.setTotal(listAmounts.get(0).getAmount() + t.getAmount());
                bo.setAmount(t.getAmount());
                bo.setTransactionId(t.getTransactionId());
                listAmounts.add(bo);
                transactionsGroup.put(t.getClient(), listAmounts);
            } else {
                List<TransactionBo> listAmounts = new ArrayList<>();
                TransactionBo bo = new TransactionBo();
                bo.setTotal(t.getAmount());
                bo.setAmount(t.getAmount());
                bo.setTransactionId(t.getTransactionId());
                listAmounts.add(bo);
                transactionsGroup.put(t.getClient(), listAmounts);
            }
        }
        return transactionsGroup;
    }

    private void updateTransaction(Long transactionId, long disbursementId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction == null) throw new RuntimeException();
        transaction.setDisbursementId(disbursementId);
        transaction.setIsDisbursement(true);
        transactionRepository.save(transaction);

    }

    private boolean debtFromCard(String card, Double amount) {
        Random random = new Random();
        LOGGER.info("Debiting from card: {} the amount of {}", card, amount);
        return random.nextInt(10) > 1;
    }

}

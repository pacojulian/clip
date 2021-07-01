package com.paco.clip.app.services.impl;

import com.paco.clip.app.exceptions.InternalServerErrorException;
import com.paco.clip.app.services.ClipServices;
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
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.paco.clip.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class ClipServicesImpl implements ClipServices {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final DisbursementRepository disbursementRepository;

    private final TransactionBuilder transactionBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClipServicesImpl.class);

    @Override
    public ClipResponse makeTransaction(MakeTransactionRequest request) {
        LOGGER.info("Init transaction");
        ClipResponse response = new ClipResponse();
        try {
            User user = userRepository.findByClipId(request.getClipUser());
            if (user == null) {
                LOGGER.error(USER_NOT_FOUND);
                throw new NotFoundException(USER_NOT_FOUND);
            }
            LOGGER.info("User Exists with id: {}", request.getClipUser());
            if (!debtFromCard(request.getCard(), request.getAmount()))
                throw new InternalServerErrorException(ERROR_DEBITING);
            LOGGER.info("Saving transaction");
            transactionRepository.save(transactionBuilder.buildTransactionObject(request));
            response.setMessage(SUCCESS_OPERATION);
            response.setStatusCode(200);
        } catch (Exception e) {
            LOGGER.error("There was an error making a transaction caused by: {}", e.getMessage());
            response.setMessage(FAIL_OPERATION);
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public List<Transaction> getTransactionsByUser(String user) {
        LOGGER.info("Get transactions for user {}", user);
        try {
            List<Transaction> response = transactionRepository.findAllByClipUser(user);
            if (response == null || response.isEmpty()) {
                LOGGER.error("There are no transactions for that user");
                throw new NotFoundException(TRANSACTION_NOT_FOUND);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error("Error obteniendo las transacciones del usuario: {}", user);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public List<DisbursementResponse> makeDisbursement(String user) {
        try {
            List<DisbursementResponse> response = new ArrayList<>();
            List<Transaction> transactionList = transactionRepository.findAllByClipUserAndIsDisbursementFalseOrderByClient(user);
            if (transactionList.isEmpty()) throw new NotFoundException(TRANSACTION_NOT_FOUND);
            Map<String, List<TransactionBo>> transactionsGroup = groupByTransaction(transactionList);
            transactionsGroup.forEach((k, v) -> {
                List<Double> transactionsAmount = new ArrayList<>();
                DisbursementResponse disResponse = new DisbursementResponse();
                Double total = v.get(v.size() - 1).getTotal();
                Disbursement disbursement = transactionBuilder.buildDisbursement(user, total);
                disbursementRepository.save(disbursement);
                v.forEach(transactionBo -> {
                    try {
                        updateTransaction(transactionBo.getTransactionId(), disbursement.getDisbursementId());
                    } catch (Exception e) {
                        throw new InternalServerErrorException(TRANSACTION_ERROR);
                    }
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
        } catch (Exception e) {
            LOGGER.error("There was an error making a disbursement");
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Disbursement> getDisbursementByUSer(String user){
       try {
           LOGGER.info("obtaining all disbursements from user {}", user);
           List<Disbursement> response = disbursementRepository.findAllByDestinationUser(user);
           if (response == null || response.isEmpty()) {
               LOGGER.error("There are no Disbursements for that user");
               throw new NotFoundException(DISBURSEMENT_NOT_FOUND);
           }
           return response;
       }catch (Exception e){
           LOGGER.error("Error getting disbursements for user : {}", user);
           throw new InternalServerErrorException(e.getMessage());
       }
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

    private void updateTransaction(Long transactionId, long disbursementId) throws NotFoundException {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction == null) throw new NotFoundException(TRANSACTION_NOT_FOUND);
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

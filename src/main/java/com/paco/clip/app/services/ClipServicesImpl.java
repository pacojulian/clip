package com.paco.clip.app.services;

import com.paco.clip.domain.builder.TransactionBuilder;
import com.paco.clip.domain.model.User;
import com.paco.clip.domain.repository.TransactionRepository;
import com.paco.clip.domain.repository.UserRepository;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ClipServicesImpl implements ClipServices {

    //Todo exception handler
    //Todo add validation

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionBuilder transactionBuilder;


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
            if (!debtFromCard(request.getCard(),request.getAmount())) throw new RuntimeException();
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
    public TransactionsResponse getTransactionsByUser(String user) {
        return null;
    }

    @Override
    public ClipResponse makeDisbursement(String user) {
        return null;
    }

    @Override
    public DisbursementResponse getDisbursementByUSer(String user) {
        return null;
    }

    private boolean debtFromCard(String card, Double amount) {
        Random random = new Random();
        LOGGER.info("Debiting from card: {} the amount of {}", card, amount);
        return random.nextInt(10) > 1;
    }

}

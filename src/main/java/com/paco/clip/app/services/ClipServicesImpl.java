package com.paco.clip.app.services;

import com.paco.clip.domain.builder.TransactionBuilder;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.domain.model.User;
import com.paco.clip.domain.repository.TransactionRepository;
import com.paco.clip.domain.repository.UserRepository;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(response == null || response.isEmpty()){
            LOGGER.error("There are no transactions for that user");
            throw new RuntimeException();
        }
        return response;
    }

    @Override
    public ClipResponse makeDisbursement(String user) {
        /**
         *  Refactor para obtener el clippy
         *  obtener las transacciones de un usuario filtradas por si pueden ser operadas
         *  Calcular el Total
         *  crear los depositos por grupo de transacciones y actualizar las transacciones para que tengan las relaciones.
         *  regresar objeto parseado
         *
         */
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

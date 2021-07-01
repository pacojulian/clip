package com.paco.clip.app.services;

import com.paco.clip.domain.builder.TransactionBuilder;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.domain.model.User;
import com.paco.clip.domain.repository.TransactionRepository;
import com.paco.clip.domain.repository.UserRepository;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ClipServicesTest {

    ClipServicesImpl clipServices;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionBuilder transactionBuilder;

    @BeforeEach
    public void setUp() {
        clipServices = new ClipServicesImpl(userRepository, transactionRepository, transactionBuilder);
    }

    @Test
    void testMakeTransaction() {
        ClipResponse assertion = new ClipResponse();
        assertion.setStatusCode(200);
        assertion.setMessage("Operacion guardada correctamente");

        Mockito.lenient().when(userRepository.findByClipId(Mockito.anyString())).thenReturn(buildUser());
        Mockito.lenient().when(transactionRepository.save(buildTransaction())).thenReturn(buildTransaction());
        ClipResponse response = clipServices.makeTransaction(getRequest());
        assertEquals(assertion, response);
    }

    @Test
    void testGetAllTransactionsFromUser() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(buildTransaction());
        Mockito.lenient().when(transactionRepository.findAllByClipUser(Mockito.anyString())).thenReturn(transactions);
        List<Transaction> response = clipServices.getTransactionsByUser(Mockito.anyString());
        assertNotNull(response);
    }

    private MakeTransactionRequest getRequest() {
        MakeTransactionRequest request = new MakeTransactionRequest();
        request.setAmount(100.0);
        request.setClipUser("Allan96");
        request.setDate("12/12/12");
        request.setCard("1234567890");
        return request;
    }

    private User buildUser() {
        User user = new User();
        user.setAccount("123455");
        user.setAccountLine(100.0);
        user.setClipId("allanjulian96");
        user.setName("Paco");
        user.setPwd("patito");
        return user;
    }

    private Transaction buildTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(100L);
        transaction.setCardData("213");
        transaction.setClipUser("allanjulian96");
        transaction.setDate(new Timestamp(System.currentTimeMillis()));
        transaction.setAmount(100.0);
        transaction.setIsDisbursement(false);
        return transaction;
    }
}

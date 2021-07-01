package com.paco.clip.app.services;

import com.paco.clip.app.services.impl.ClipServicesImpl;
import com.paco.clip.domain.builder.TransactionBuilder;
import com.paco.clip.domain.model.Disbursement;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.domain.model.User;
import com.paco.clip.domain.repository.DisbursementRepository;
import com.paco.clip.domain.repository.TransactionRepository;
import com.paco.clip.domain.repository.UserRepository;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Mock
    private DisbursementRepository disbursementRepository;

    @BeforeEach
    public void setUp() {
        clipServices = new ClipServicesImpl(userRepository, transactionRepository,disbursementRepository,transactionBuilder);
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

    @Test
    void testMakeDisbursement(){
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(buildTransaction());
        Mockito.lenient().when(transactionRepository.findAllByClipUserAndIsDisbursementFalseOrderByClient(Mockito.anyString())).thenReturn(transactionsList);
        Mockito.lenient().when(transactionRepository.findByTransactionId(Mockito.anyLong())).thenReturn(buildTransaction());
        Mockito.lenient().when(transactionRepository.save(buildTransaction())).thenReturn(buildTransaction());
        Mockito.lenient().when(disbursementRepository.save(buildDisbursement())).thenReturn(buildDisbursement());
        Mockito.lenient().when(transactionBuilder.buildDisbursement(Mockito.anyString(),Mockito.anyDouble())).thenReturn(buildDisbursement());
        List<DisbursementResponse> response = clipServices.makeDisbursement("allan96");
        assertNotNull(response);
    }

    @Test
    void testGetAllDisbursementsFromUser() {
        List<Disbursement> disbursements = new ArrayList<>();
        disbursements.add(buildDisbursement());
        Mockito.lenient().when(disbursementRepository.findAllByDestinationUser(Mockito.anyString())).thenReturn(disbursements);
        List<Disbursement> response = clipServices.getDisbursementByUSer(Mockito.anyString());
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

    private Disbursement buildDisbursement(){
        Disbursement disbursement = new Disbursement();
        disbursement.setDisbursementId(Math.abs(new Random().nextLong()));
        disbursement.setAmount(100.0);
        disbursement.setDestinationUser("allan96");
        disbursement.setDate(new Timestamp(System.currentTimeMillis()));
        return  disbursement;
    }
}

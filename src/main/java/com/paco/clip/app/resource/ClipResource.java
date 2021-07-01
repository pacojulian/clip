package com.paco.clip.app.resource;

import com.paco.clip.domain.model.Transaction;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/clip")
public interface ClipResource {

    /**
     * @param request includes the destination and amount to whom you are transaction it
     * @return message and status code if the transaction was successful
     */
    @ApiOperation(value = "Make a transaction", response = ClipResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creado"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 500, message = "Error al procesar la solicitud"),
    })
    @PostMapping(value = "/v1/transaction")
    ClipResponse makeTransaction(@RequestBody MakeTransactionRequest request);

    /**
     * @param user the id which user you are trying to retrieve the information
     * @return list of transactions that a certain user has received.
     */
    @ApiOperation(value = "Obtain transactions of a user", response = TransactionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 404, message = "No se encontraron recursos"),
            @ApiResponse(code = 500, message = "Error al procesar la solicitud"),
    })
    @GetMapping(value = "/v1/transaction")
    List<Transaction> getTransactionsByUser(@RequestParam("user") String user);

    /**
     * @param user which is going to receive the disbursements
     * @return message and status code if the transaction was successful
     */
    @ApiOperation(value = "Make disbursement of a user", response = ClipResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creado"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 500, message = "Error al procesar la solicitud"),
    })
    @PostMapping(value = "/v1/disbursement")
    List<DisbursementResponse> makeDisbursement(@RequestParam("user") String user);


    /**
     * @param user id to see information
     * @return list of all disbursements made by a user.
     */
    @ApiOperation(value = "Get all disbursement of a user", response = DisbursementResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 404, message = "No se encontraron recursos"),
            @ApiResponse(code = 500, message = "Error al procesar la solicitud"),
    })
    @GetMapping(value = "/v1/disbursement")
    DisbursementResponse getDisbursementByUSer(@RequestParam("user") String user);

}

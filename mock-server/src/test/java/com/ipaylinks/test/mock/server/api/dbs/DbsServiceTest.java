package com.ipaylinks.test.mock.server.api.dbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipaylinks.cfp.spring.boot.autoconfigure.dbs.DbsService;
import com.ipaylinks.cfp.spring.boot.autoconfigure.dbs.dto.req.*;
import com.ipaylinks.test.mock.server.MockServerApplication;

import org.bouncycastle.openpgp.PGPException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServerApplication.class)
public class DbsServiceTest {
    @Autowired
    private DbsService dbsService;
    private Map<String, String> cache = new ConcurrentHashMap<>();

    @Test
    public void multiThreadTest(){

        int threadCount = 2;
        int loopCount = 10;
        CountDownLatch countDownLatch=new CountDownLatch(threadCount);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(()->{
                for (int j = 0; j < loopCount; j++) {
                    System.out.println(Thread.currentThread().getName() + "threadCount-->>>>" + finalI +";loopCount-->>>>" +j );
//                    quote();
                    accountBalanceEnquiry();
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {
        quote();
        Thread.sleep(2000L);
        booking();
        Thread.sleep(2000L);
        act();
//        tt();
//        transactionEnquiry();
//        accountBalanceEnquiry();
    }

    public void quote() {
        QuoteReqDTO quoteReqDTO = new QuoteReqDTO();
        QuoteHeader header = new QuoteHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        quoteReqDTO.setHeader(header);
        QuoteTxnInfo txnInfo = new QuoteTxnInfo();
        txnInfo.setCcyPair("EURUSD");
        txnInfo.setDealtSide("BUY");
        txnInfo.setTxnAmount("1000");
        txnInfo.setTxnCcy("USD");
        txnInfo.setTenor("TODAY");

        String clientTxnsId = "clientTxnsId"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        cache.put("clientTxnsId", clientTxnsId);
        txnInfo.setClientTxnsId(clientTxnsId);
        quoteReqDTO.setTxnInfo(txnInfo);
        try {
            String decryptedRespStr = dbsService.quote(quoteReqDTO);

            JSONObject resp = JSON.parseObject(decryptedRespStr);
            JSONObject txnResponse = resp.getJSONObject("txnResponse");
            String uid = txnResponse.getString("uid");
            cache.put("uid", uid);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }

    public void booking() {
        BookingReqDTO bookingReqDTO = new BookingReqDTO();
        BookingHeader header = new BookingHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        bookingReqDTO.setHeader(header);
        BookingTxnInfo txnInfo = new BookingTxnInfo();
        txnInfo.setUid(cache.get("uid"));
        txnInfo.setClientTxnsId(cache.get("clientTxnsId"));
        bookingReqDTO.setTxnInfo(txnInfo);
        try {
            String decryptedRespStr = dbsService.booking(bookingReqDTO);

            JSONObject resp = JSON.parseObject(decryptedRespStr);
            JSONObject txnResponse = resp.getJSONObject("txnResponse");
            String txnRefId = txnResponse.getString("txnRefId");
            cache.put("txnRefId", txnRefId);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }

    public void act() {
        ActReqDTO actReqDTO = new ActReqDTO();

        ActHeader header = new ActHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        header.setCtry("CN");
        header.setNoOfTxs("1");
        header.setTotalTxnAmount("1000");
        actReqDTO.setHeader(header);

        ActTxnInfoDetails txnInfoDetails = new ActTxnInfoDetails();
        List<ActTxnInfo> txnInfo = new ArrayList<>();
        ActTxnInfo txnInfo1 = new ActTxnInfo();
        String customerReference = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
        cache.put("customerReference", customerReference);
        txnInfo1.setCustomerReference(customerReference);
        txnInfo1.setTxnType("ACT");
        txnInfo1.setTxnDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txnInfo1.setTxnCcy("USD");
        txnInfo1.setTxnAmount("1000");
        txnInfo1.setDebitAccountCcy("EUR");
        txnInfo1.setDebitAccountAmount("");
        txnInfo1.setFxContractRef1(cache.get("txnRefId"));
        txnInfo1.setFxAmountUtilized1("1000");
        txnInfo1.setFxContractRef2("");
        txnInfo1.setFxAmountUtilized2("");

        TxnInfoSenderParty senderParty = new TxnInfoSenderParty();
        senderParty.setName("iPayLinks LIMITED");
        senderParty.setAccountNo("NRA200039681EUR");
        senderParty.setBankCtryCode("CN");
        senderParty.setSwiftBic("DBSSCNSHXXX");
        txnInfo1.setSenderParty(senderParty);

        ActTxnInfoReceivingParty receivingParty = new ActTxnInfoReceivingParty();
        receivingParty.setName("iPayLinks LIMITED");
        receivingParty.setAccountNo("NRA200039681USD");
        txnInfo1.setReceivingParty(receivingParty);

        TxnInfoAdviseDelivery adviseDelivery = new TxnInfoAdviseDelivery();
        adviseDelivery.setMode("ES");
        List<TxnInfoAdviseDeliveryEmail> emails = new ArrayList<>();
        TxnInfoAdviseDeliveryEmail email1 = new TxnInfoAdviseDeliveryEmail();
        email1.setEmail("qiuhua.yang@ipaylinks.com");
        emails.add(email1);
        TxnInfoAdviseDeliveryEmail email2 = new TxnInfoAdviseDeliveryEmail();
        email2.setEmail("zhenhua.cao@ipaylinks.com");
        emails.add(email2);
        adviseDelivery.setEmails(emails);

        List<TxnInfoAdviseDeliveryPhoneNumber> phoneNumbers = new ArrayList<>();
        TxnInfoAdviseDeliveryPhoneNumber phoneNumber1 = new TxnInfoAdviseDeliveryPhoneNumber();
        phoneNumber1.setPhoneNumber("15261471747");
        phoneNumbers.add(phoneNumber1);
        TxnInfoAdviseDeliveryPhoneNumber phoneNumber2 = new TxnInfoAdviseDeliveryPhoneNumber();
        phoneNumber2.setPhoneNumber("15900573137");
        phoneNumbers.add(phoneNumber2);
        adviseDelivery.setPhoneNumbers(phoneNumbers);
        txnInfo1.setAdviseDelivery(adviseDelivery);

        TxnInfoRmtInf rmtInf = new TxnInfoRmtInf();
        List<TxnInfoRmtInfClientReference> clientReferences = new ArrayList<>();
        TxnInfoRmtInfClientReference clientReference1 = new TxnInfoRmtInfClientReference();
        clientReference1.setClientReference("email content clientReference1");
        clientReferences.add(clientReference1);
        rmtInf.setClientReferences(clientReferences);

        List<TxnInfoRmtInfInvoiceDetail> invoiceDetails = new ArrayList<>();
        TxnInfoRmtInfInvoiceDetail invoiceDetail1 = new TxnInfoRmtInfInvoiceDetail();
        invoiceDetail1.setInvoice("email content invoice1");
        invoiceDetails.add(invoiceDetail1);
        TxnInfoRmtInfInvoiceDetail invoiceDetail2 = new TxnInfoRmtInfInvoiceDetail();
        invoiceDetail2.setInvoice("email content invoice2");
        invoiceDetails.add(invoiceDetail2);
        rmtInf.setInvoiceDetails(invoiceDetails);

        List<TxnInfoRmtInfPaymentDetail> paymentDetails = new ArrayList<>();
        TxnInfoRmtInfPaymentDetail paymentDetail1 = new TxnInfoRmtInfPaymentDetail();
        paymentDetail1.setPaymentDetail("email content paymentDetail1");
        paymentDetails.add(paymentDetail1);
        rmtInf.setPaymentDetails(paymentDetails);
        txnInfo1.setRmtInf(rmtInf);

        txnInfo.add(txnInfo1);
        txnInfoDetails.setTxnInfo(txnInfo);
        actReqDTO.setTxnInfoDetails(txnInfoDetails);
        try {
            String decryptedRespStr = dbsService.act(actReqDTO);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }

    public void tt() {
        TtReqDTO ttReqDTO = new TtReqDTO();

        TtHeader header = new TtHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        header.setCtry("CN");
        header.setNoOfTxs("1");
        header.setTotalTxnAmount("1000");
        ttReqDTO.setHeader(header);

        TtTxnInfoDetails txnInfoDetails = new TtTxnInfoDetails();
        List<TtTxnInfo> txnInfo = new ArrayList<>();
        TtTxnInfo txnInfo1 = new TtTxnInfo();
        String customerReference = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
        cache.put("customerReference", customerReference);
        txnInfo1.setCustomerReference(customerReference);
        txnInfo1.setTxnType("TT");
        txnInfo1.setTxnDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txnInfo1.setTxnCcy("USD");
        txnInfo1.setTxnAmount("1000");
        txnInfo1.setDebitAccountCcy("EUR");
        txnInfo1.setDebitAccountAmount("");
        txnInfo1.setFxContractRef1("");
        txnInfo1.setFxAmountUtilized1("");
        txnInfo1.setFxContractRef2("");
        txnInfo1.setFxAmountUtilized2("");
        txnInfo1.setChargeBearer("DEBT");
        txnInfo1.setDebitAccountForBankCharges("1");
        txnInfo1.setInstructionToOrderingBank("FULLPAY");

        TxnInfoSenderParty senderParty = new TxnInfoSenderParty();
        senderParty.setName("iPayLinks");
        senderParty.setAccountNo("NRA200039681USD");
        senderParty.setBankCtryCode("CN");
        senderParty.setSwiftBic("DBSSCNSHXXX");
        txnInfo1.setSenderParty(senderParty);

        TtTxnInfoReceivingParty receivingParty = new TtTxnInfoReceivingParty();
        receivingParty.setName("iPayLinks");
        receivingParty.setAccountNo("NRA200039681CNY");
        receivingParty.setBankCtryCode("SG");
        receivingParty.setSwiftBic("DBSSCNSHXXX");
//        receivingParty.setRoutingCode("SG");
        receivingParty.setBankName("");
        receivingParty.setBankAddress("");
        List<TtTxnInfoReceivingPartyBeneficiaryAddress> beneficiaryAddresses = new ArrayList<>();
        TtTxnInfoReceivingPartyBeneficiaryAddress address1 = new TtTxnInfoReceivingPartyBeneficiaryAddress();
        address1.setAddress("china sh");
        beneficiaryAddresses.add(address1);
        TtTxnInfoReceivingPartyBeneficiaryAddress address2 = new TtTxnInfoReceivingPartyBeneficiaryAddress();
        address2.setAddress("china nj");
        beneficiaryAddresses.add(address2);
        receivingParty.setBeneficiaryAddresses(beneficiaryAddresses);
        receivingParty.setIntermediarySwiftBic("");
        txnInfo1.setReceivingParty(receivingParty);

        TxnInfoAdviseDelivery adviseDelivery = new TxnInfoAdviseDelivery();
        adviseDelivery.setMode("ES");
        List<TxnInfoAdviseDeliveryEmail> emails = new ArrayList<>();
        TxnInfoAdviseDeliveryEmail email1 = new TxnInfoAdviseDeliveryEmail();
        email1.setEmail("qiuhua.yang@ipaylinks.com");
        emails.add(email1);
        TxnInfoAdviseDeliveryEmail email2 = new TxnInfoAdviseDeliveryEmail();
        email2.setEmail("zhenhua.cao@ipaylinks.com");
        emails.add(email2);
        adviseDelivery.setEmails(emails);

        List<TxnInfoAdviseDeliveryPhoneNumber> phoneNumbers = new ArrayList<>();
        TxnInfoAdviseDeliveryPhoneNumber phoneNumber1 = new TxnInfoAdviseDeliveryPhoneNumber();
        phoneNumber1.setPhoneNumber("15261471747");
        phoneNumbers.add(phoneNumber1);
        TxnInfoAdviseDeliveryPhoneNumber phoneNumber2 = new TxnInfoAdviseDeliveryPhoneNumber();
        phoneNumber2.setPhoneNumber("15900573137");
        phoneNumbers.add(phoneNumber2);
        adviseDelivery.setPhoneNumbers(phoneNumbers);
        txnInfo1.setAdviseDelivery(adviseDelivery);

        TxnInfoRmtInf rmtInf = new TxnInfoRmtInf();
        List<TxnInfoRmtInfClientReference> clientReferences = new ArrayList<>();
        TxnInfoRmtInfClientReference clientReference1 = new TxnInfoRmtInfClientReference();
        clientReference1.setClientReference("email content clientReference1");
        clientReferences.add(clientReference1);
        rmtInf.setClientReferences(clientReferences);

        List<TxnInfoRmtInfInvoiceDetail> invoiceDetails = new ArrayList<>();
        TxnInfoRmtInfInvoiceDetail invoiceDetail1 = new TxnInfoRmtInfInvoiceDetail();
        invoiceDetail1.setInvoice("email content invoice1");
        invoiceDetails.add(invoiceDetail1);
        TxnInfoRmtInfInvoiceDetail invoiceDetail2 = new TxnInfoRmtInfInvoiceDetail();
        invoiceDetail2.setInvoice("email content invoice2");
        invoiceDetails.add(invoiceDetail2);
        rmtInf.setInvoiceDetails(invoiceDetails);

        List<TxnInfoRmtInfPaymentDetail> paymentDetails = new ArrayList<>();
        TxnInfoRmtInfPaymentDetail paymentDetail1 = new TxnInfoRmtInfPaymentDetail();
        paymentDetail1.setPaymentDetail("email content paymentDetail1");
        paymentDetails.add(paymentDetail1);
        rmtInf.setPaymentDetails(paymentDetails);
        txnInfo1.setRmtInf(rmtInf);

        TtTxnInfoBopInfo bopInfo = new TtTxnInfoBopInfo();
        bopInfo.setSpecificPaymentPurpose("AP");
        bopInfo.setTaxFreeGoodsRelated("Y");
        bopInfo.setPaymentNature("fdgfdg1");
        bopInfo.setReferenceNo("34324355");
        bopInfo.setbOPCode1PaymentCategory("TR");
        bopInfo.setbOPCode1SeriesCode("121010");
        bopInfo.setContractNo("343");
        bopInfo.setInvoiceNo("");
        bopInfo.setTransactionRemarks1("transactionRemarks1");
        bopInfo.setTradeType("GT");
        bopInfo.setTxnTypeCHN("TS");
        bopInfo.setProportionOfContractAmount("1");
        bopInfo.setExpectedDeclarationDays("12");
        bopInfo.setDeclaredCustomsOrNot("DC");
        bopInfo.setCustomsDeclarationCcy("CNY");
        bopInfo.setApprovalCertificateNo("");
        bopInfo.setAdvancePaymentAmount("12");
        bopInfo.setRemarksCHN("remark");
        bopInfo.setTradeTypeCustomsDeclaration("B");
        txnInfo1.setBopInfo(bopInfo);

        txnInfo.add(txnInfo1);
        txnInfoDetails.setTxnInfo(txnInfo);
        ttReqDTO.setTxnInfoDetails(txnInfoDetails);
        try {
            String decryptedRespStr = dbsService.tt(ttReqDTO);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }

    @Test
    public void accountBalanceEnquiry() {
        AccountBalanceEnquiryReqDTO transactionEnquiryReqDTO = new AccountBalanceEnquiryReqDTO();
        AccountBalanceEnquiryHeader header = new AccountBalanceEnquiryHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        header.setCtry("CN");
        transactionEnquiryReqDTO.setHeader(header);

        AccountBalanceEnquiryTxnInfo txnInfo = new AccountBalanceEnquiryTxnInfo();
        txnInfo.setTxnType("BLE");
        transactionEnquiryReqDTO.setTxnInfo(txnInfo);

        AccountBalanceEnquiryAccountBalInfo accountBalInfo = new AccountBalanceEnquiryAccountBalInfo();
        accountBalInfo.setAccountNo("NRA200039681USD");
        accountBalInfo.setAccountCcy("USD");
        transactionEnquiryReqDTO.setAccountBalInfo(accountBalInfo);
        try {
            String decryptedRespStr = dbsService.accountBalanceEnquiry(transactionEnquiryReqDTO);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }

    public void transactionEnquiry() {
        TransactionEnquiryReqDTO transactionEnquiryReqDTO = new TransactionEnquiryReqDTO();
        TransactionEnquiryHeader header = new TransactionEnquiryHeader();
        header.setMsgId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        header.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        header.setCtry("CN");
        transactionEnquiryReqDTO.setHeader(header);

        TransactionEnquiryTxnInfo txnInfo = new TransactionEnquiryTxnInfo();
        txnInfo.setTxnType("TSE");
        txnInfo.setEnqType("RET");
        transactionEnquiryReqDTO.setTxnInfo(txnInfo);

        TransactionEnquiryTxnEnquiry txnEnquiry = new TransactionEnquiryTxnEnquiry();
        txnEnquiry.setEnqAccountNo("NRA200039681USD");
//        txnEnquiry.setVpa("");
        txnEnquiry.setCustomerReference("190528165422103");
//        txnEnquiry.setTxnRefId("");
//        txnEnquiry.setRefId("");
        transactionEnquiryReqDTO.setTxnEnquiry(txnEnquiry);
        try {
            String decryptedRespStr = dbsService.transactionEnquiry(transactionEnquiryReqDTO);
        } catch (PGPException | IOException e) {
//            TODO
        }
    }
}
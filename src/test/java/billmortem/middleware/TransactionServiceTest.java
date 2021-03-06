package billmortem.middleware;

import billmortam.pdf.Pdf;
import billmortem.util.APITestHelper;
import billmortem.util.MultiPartUtility;
import billmortem.util.TimeComplexityTestCase;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class TransactionServiceTest extends TimeComplexityTestCase {

    @Test
    public void testerror1301() throws Exception {
        File file = new File(new File("src/test/res/sample.pdf").getCanonicalPath());

        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFilePart("file1", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");
        ServiceError serviceErrorResult = new Gson().fromJson(result,ServiceError.class);
        ServiceError serviceErrorActual = new ServiceError(1301,"Not able to process");

        Assert.assertEquals(serviceErrorActual.toString(), serviceErrorResult.toString());
    }

    @Test
    public void testerror1303() throws Exception {
        File file = new File(new File("src/test/res/sample_password_protected_bill.pdf").getCanonicalPath());

        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFilePart("file", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");
        ServiceError serviceErrorResult = new Gson().fromJson(result,ServiceError.class);
        ServiceError serviceErrorActual = new ServiceError(1303,"Password required");

        Assert.assertEquals(serviceErrorActual.toString(), serviceErrorResult.toString());
    }

    @Test
    public void testerror1302() throws Exception {
        File file = new File(new File("src/test/res/1501762578649.txt").getCanonicalPath());
        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFilePart("file", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");
        ServiceError serviceErrorResult = new Gson().fromJson(result,ServiceError.class);
        ServiceError serviceErrorActual = new ServiceError(1302,"Invalid pdf file");

        Assert.assertEquals(serviceErrorActual.toString(), serviceErrorResult.toString());
    }

    @Test
    public void fileUploadPasswordProtectedFile() throws Exception {
        File file = new File(new File("src/test/res/sample_password_protected_bill.pdf").getCanonicalPath());

        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFormField("password", "1234");
        http.addFilePart("file", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");

        Assert.assertEquals( 12891,result.length());
    }

    @Test
    public void test1304() throws Exception {
        File file = new File(new File("src/test/res/sample_password_protected_bill.pdf").getCanonicalPath());

        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFormField("password", "PRAD0280");
        http.addFilePart("file", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");

        ServiceError serviceErrorResult = new Gson().fromJson(result,ServiceError.class);
        ServiceError serviceErrorActual = new ServiceError(1304,"Password invalid");

        Assert.assertEquals(serviceErrorActual.toString(), serviceErrorResult.toString());
    }

    @Test
    public void fileUploadTest() throws Exception {
        File file = new File(new File("src/test/res/sample.pdf").getCanonicalPath());

        MultiPartUtility http = new MultiPartUtility(new URL("http://localhost:8080/service/transaction/get_transactions1"));
        http.addFilePart("file", file);
        final byte[] bytes = http.finish();
        String result = new String(bytes, "UTF-8");

        Assert.assertEquals(12805, result.length());

    }

    @Test
    public void convertRawPdfToTransactionObject() throws Exception {
        TransactionService transaction = new TransactionService();
        Pdf pdf = new Pdf();
        pdf.setData(raw);
        String transacitonAsJson = transaction.getTransactionAsJson(pdf);
        String actual = "{\"contents\":[{\"price\":356.5,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"WWW.VODAFONE.IN        MUMBAI\"},{\"price\":33.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":220.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"SOUTH PARK CAFE \\u0026 CANT PUNE\"},{\"price\":500.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":100.0,\"date\":\"06/05/2017\",\"isCredit\":false,\"description\":\"WWW OLACABS COM        GURGAON\"},{\"price\":511.5,\"date\":\"06/05/2017\",\"isCredit\":false,\"description\":\"BALAJI PETROLEUM       NAGPUR\"},{\"price\":3.75,\"date\":\"06/05/2017\",\"isCredit\":true,\"description\":\"HPCL 0.75% CASHLESS IN MUMBAI\"},{\"price\":5.06,\"date\":\"06/05/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"},{\"price\":2.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":120.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":500.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"VIDEOCOND2H_TPSL       MUMBA\"},{\"price\":30.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":8.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":300.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":185.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"RADHA KRISHNA PURE VEG PUNE\"},{\"price\":100.0,\"date\":\"15/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":100.0,\"date\":\"16/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":64.0,\"date\":\"17/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"17/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":760.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PATANJALI              PUNE\"},{\"price\":44.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":1450.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"VITALIFE HEALTH SERVICEPUNE\"},{\"price\":400.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"ACCLAIM HEALTH CARE PV PUNE\"},{\"price\":30.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":180.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"ANIL JEWELLERS         PUNE\"},{\"price\":127.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"FARMERS \\u0026 CROCERS      PUNE\"},{\"price\":280.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"RUCHIRA WINES          PUNE\"},{\"price\":585.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"GETWELL CHEMIST AND DRUPUNE\"},{\"price\":100.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":170.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"RACHIRA WINES          PUNE\"},{\"price\":25740.97,\"date\":\"19/05/2017\",\"isCredit\":true,\"description\":\"TELE TRANSFER CREDIT\"},{\"price\":120.0,\"date\":\"20/05/2017\",\"isCredit\":false,\"description\":\"RAJMANDIR ICECREAM BANEPUNE\"},{\"price\":400.0,\"date\":\"21/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":5.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":70.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":33.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":514.37,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"AMITA ENTERPRISES A P  PUNE\"},{\"price\":20.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":5.09,\"date\":\"22/05/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"},{\"price\":100.0,\"date\":\"23/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"23/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":24.66,\"date\":\"24/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":23.0,\"date\":\"24/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":60.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":64.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":35.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":200.0,\"date\":\"26/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":85.0,\"date\":\"26/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":45.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":40.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":50.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":343.33,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"AMITA ENTERPRISES A P  PUNE\"},{\"price\":2799.0,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"MITUL ENTERPRISES PVT  MUMBAI\"},{\"price\":92.58,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"BURGER KING INDIA PVT LPUNE\"},{\"price\":180.0,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"PANCAKE FACTORY        PUNE\"},{\"price\":100.0,\"date\":\"29/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":70.0,\"date\":\"29/05/2017\",\"isCredit\":false,\"description\":\"SHREE SAI ENTERPRISES  PUNE\"},{\"price\":10.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":48.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":35.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":120.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":920.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"AUTHENTIC AESTHETIC CENPUNE\"},{\"price\":36.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"NEW PRASHANT ENTERPRIS PUNE\"},{\"price\":130.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":45.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PRABHU CATERERS        PUNE\"},{\"price\":414.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"MICROSOFT1291INRUSDB2C MSBILL.INFO\"},{\"price\":36.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"NEW PRASHANT ENTERPRIS PUNE\"},{\"price\":2.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":15.0,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":1011.5,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"SHELL INDIA MARKETS PV PUNE\"},{\"price\":50.0,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":10.01,\"date\":\"02/06/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"}],\"pdf\":{\"data\":\"In case any of your personal details have changed, you can\\nupdate the same by contacting our Customer Service.\\n \\nName : PRADEEP PANKAJ                          \\n \\n \\nStatement for Titanium Times Card Credit Card\\nStatement Date:05/06/2017     Card No: 5241 81XX XXXX 0280\\nPayment Due Date Total Dues Minimum Amount Due\\n25/06/2017 16,078.53 810.00\\nCredit Limit Available Credit Limit Available Cash Limit\\n1,71,000 1,53,067 68,400\\nAccount Summary\\nOpening\\nBalance\\nPayment/ \\nCredits\\nPurchase/ \\nDebits\\nFinance\\nCharges\\nTotal Dues\\n25,740.97 25,764.88 16,102.44 0.00 16,078.53\\nPast Dues (If any)\\nOverlimit 3 Months+ 2 Months 1 Month Current Dues\\nMinimum\\nAmount Due\\n0.00 0.00 0.00 0.00 810.00 810.00\\nIMPORTANT INFORMATION\\n1. PLEASE REVIEW THE CHANGES IN REWARD POINTS PROGRAM IN THE MITC U/S 9\\nOR CREDIT CARD PRODUCT PAGE ON BANK WEBSITE.\\nDomestic Transactions\\nDate  Transaction Description Amount (in Rs.)\\n    \\n PRADEEP PANKAJ   \\n05/05/2017 www.vodafone.in        MUMBAI 356.50  \\n05/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \\n05/05/2017 SOUTH PARK CAFE \\u0026 CANT PUNE 220.00  \\n05/05/2017 PAYTM APP              NOIDA 500.00  \\n06/05/2017 WWW OLACABS COM        GURGAON 100.00  \\n06/05/2017 BALAJI PETROLEUM       NAGPUR 511.50  \\n06/05/2017 HPCL 0.75% Cashless In MUMBAI 3.75 Cr\\n06/05/2017 PETRO SURCHARGE WAIVER 5.06 Cr\\n12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \\n12/05/2017 PAYTM APP              NOIDA 100.00  \\n12/05/2017 PAYTM APP              NOIDA 120.00  \\n12/05/2017 videocond2h_TPSL       MUMBA 500.00  \\n12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 8.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 300.00  \\n13/05/2017 RADHA KRISHNA PURE VEG PUNE 185.00  \\nTitanium Times Card Credit Card Statement\\n Page 1 of 4\\nDomestic Transactions\\nDate  Transaction Description Amount (in Rs.)\\n    \\n PRADEEP PANKAJ   \\n15/05/2017 PAYTM APP              NOIDA 100.00  \\n16/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \\n17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n18/05/2017 PATANJALI              PUNE 760.00  \\n18/05/2017 PAYTM APP              NOIDA 44.00  \\n18/05/2017 VITALIFE HEALTH SERVICEPUNE 1,450.00  \\n18/05/2017 ACCLAIM HEALTH CARE PV PUNE 400.00  \\n18/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \\n18/05/2017 PAYTM APP              NOIDA 100.00  \\n19/05/2017 ANIL JEWELLERS         PUNE 180.00  \\n19/05/2017 FARMERS \\u0026 CROCERS      PUNE 127.00  \\n19/05/2017 RUCHIRA WINES          PUNE 280.00  \\n19/05/2017 GETWELL CHEMIST AND DRUPUNE 585.00  \\n19/05/2017 PAYTM APP              NOIDA 100.00  \\n19/05/2017 RACHIRA WINES          PUNE 170.00  \\n19/05/2017 TELE TRANSFER CREDIT 25,740.97 Cr\\n20/05/2017 RAJMANDIR ICECREAM BANEPUNE 120.00  \\n21/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 400.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 5.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 70.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \\n22/05/2017 AMITA ENTERPRISES A P  PUNE 514.37  \\n22/05/2017 PAYTM APP              NOIDA 20.00  \\n22/05/2017 PETRO SURCHARGE WAIVER 5.09 Cr\\n23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n24/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 24.66  \\n24/05/2017 PAYTM APP              NOIDA 23.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 60.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \\n26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 200.00  \\n26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 85.00  \\n27/05/2017 PAYTM APP              NOIDA 45.00  \\n27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 40.00  \\n27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \\n27/05/2017 AMITA ENTERPRISES A P  PUNE 343.33  \\n28/05/2017 MITUL ENTERPRISES PVT  MUMBAI 2,799.00  \\n28/05/2017 BURGER KING INDIA PVT LPUNE 92.58  \\n28/05/2017 PANCAKE FACTORY        PUNE 180.00  \\n29/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n29/05/2017 SHREE SAI ENTERPRISES  PUNE 70.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 10.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 48.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \\n31/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 120.00  \\n31/05/2017 AUTHENTIC AESTHETIC CENPUNE 920.00  \\n31/05/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \\n01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 130.00  \\n01/06/2017 PRABHU CATERERS        PUNE 45.00  \\n01/06/2017 MICROSOFT1291INRUSDB2C MSBILL.INFO 414.00  \\n01/06/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \\n01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \\n02/06/2017 PAYTM APP              NOIDA 15.00  \\n02/06/2017 SHELL INDIA MARKETS PV PUNE 1,011.50  \\n02/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \\n02/06/2017 PETRO SURCHARGE WAIVER 10.01 Cr\\nReward Points Summary\\nTitanium Times Card Credit Card Statement\\n Page 2 of 4\"}}";
        Assert.assertEquals(actual, transacitonAsJson);
    }

    @Test
    public void testDistinctReportService() throws Exception {
        APITestHelper helper = new APITestHelper();
        Pdf pdf = new Pdf();
        pdf.setData(raw);
        System.out.println(new Gson().toJson(pdf));
        String result = helper.doPostSynchronously("http://localhost:8080/service/transaction/get_transactions", new Gson().toJson(pdf));
        String actual = "{\"contents\":[{\"price\":356.5,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"WWW.VODAFONE.IN        MUMBAI\"},{\"price\":33.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":220.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"SOUTH PARK CAFE \\u0026 CANT PUNE\"},{\"price\":500.0,\"date\":\"05/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":100.0,\"date\":\"06/05/2017\",\"isCredit\":false,\"description\":\"WWW OLACABS COM        GURGAON\"},{\"price\":511.5,\"date\":\"06/05/2017\",\"isCredit\":false,\"description\":\"BALAJI PETROLEUM       NAGPUR\"},{\"price\":3.75,\"date\":\"06/05/2017\",\"isCredit\":true,\"description\":\"HPCL 0.75% CASHLESS IN MUMBAI\"},{\"price\":5.06,\"date\":\"06/05/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"},{\"price\":2.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":120.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":500.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"VIDEOCOND2H_TPSL       MUMBA\"},{\"price\":30.0,\"date\":\"12/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":8.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":300.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":185.0,\"date\":\"13/05/2017\",\"isCredit\":false,\"description\":\"RADHA KRISHNA PURE VEG PUNE\"},{\"price\":100.0,\"date\":\"15/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":100.0,\"date\":\"16/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":64.0,\"date\":\"17/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"17/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":760.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PATANJALI              PUNE\"},{\"price\":44.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":1450.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"VITALIFE HEALTH SERVICEPUNE\"},{\"price\":400.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"ACCLAIM HEALTH CARE PV PUNE\"},{\"price\":30.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"18/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":180.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"ANIL JEWELLERS         PUNE\"},{\"price\":127.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"FARMERS \\u0026 CROCERS      PUNE\"},{\"price\":280.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"RUCHIRA WINES          PUNE\"},{\"price\":585.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"GETWELL CHEMIST AND DRUPUNE\"},{\"price\":100.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":170.0,\"date\":\"19/05/2017\",\"isCredit\":false,\"description\":\"RACHIRA WINES          PUNE\"},{\"price\":25740.97,\"date\":\"19/05/2017\",\"isCredit\":true,\"description\":\"TELE TRANSFER CREDIT\"},{\"price\":120.0,\"date\":\"20/05/2017\",\"isCredit\":false,\"description\":\"RAJMANDIR ICECREAM BANEPUNE\"},{\"price\":400.0,\"date\":\"21/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":5.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":70.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":33.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":514.37,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"AMITA ENTERPRISES A P  PUNE\"},{\"price\":20.0,\"date\":\"22/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":5.09,\"date\":\"22/05/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"},{\"price\":100.0,\"date\":\"23/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":100.0,\"date\":\"23/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":24.66,\"date\":\"24/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":23.0,\"date\":\"24/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":60.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":64.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":35.0,\"date\":\"25/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":200.0,\"date\":\"26/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":85.0,\"date\":\"26/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":45.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":40.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":50.0,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":343.33,\"date\":\"27/05/2017\",\"isCredit\":false,\"description\":\"AMITA ENTERPRISES A P  PUNE\"},{\"price\":2799.0,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"MITUL ENTERPRISES PVT  MUMBAI\"},{\"price\":92.58,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"BURGER KING INDIA PVT LPUNE\"},{\"price\":180.0,\"date\":\"28/05/2017\",\"isCredit\":false,\"description\":\"PANCAKE FACTORY        PUNE\"},{\"price\":100.0,\"date\":\"29/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":70.0,\"date\":\"29/05/2017\",\"isCredit\":false,\"description\":\"SHREE SAI ENTERPRISES  PUNE\"},{\"price\":10.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":48.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":35.0,\"date\":\"30/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":120.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":920.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"AUTHENTIC AESTHETIC CENPUNE\"},{\"price\":36.0,\"date\":\"31/05/2017\",\"isCredit\":false,\"description\":\"NEW PRASHANT ENTERPRIS PUNE\"},{\"price\":130.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":45.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PRABHU CATERERS        PUNE\"},{\"price\":414.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"MICROSOFT1291INRUSDB2C MSBILL.INFO\"},{\"price\":36.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"NEW PRASHANT ENTERPRIS PUNE\"},{\"price\":2.0,\"date\":\"01/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":15.0,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"PAYTM APP              NOIDA\"},{\"price\":1011.5,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"SHELL INDIA MARKETS PV PUNE\"},{\"price\":50.0,\"date\":\"02/06/2017\",\"isCredit\":false,\"description\":\"PAYTM MOBILE SOLUT INR WWW.PAYTM.IN\"},{\"price\":10.01,\"date\":\"02/06/2017\",\"isCredit\":true,\"description\":\"PETRO SURCHARGE WAIVER\"}],\"pdf\":{\"data\":\"In case any of your personal details have changed, you can\\nupdate the same by contacting our Customer Service.\\n \\nName : PRADEEP PANKAJ                          \\n \\n \\nStatement for Titanium Times Card Credit Card\\nStatement Date:05/06/2017     Card No: 5241 81XX XXXX 0280\\nPayment Due Date Total Dues Minimum Amount Due\\n25/06/2017 16,078.53 810.00\\nCredit Limit Available Credit Limit Available Cash Limit\\n1,71,000 1,53,067 68,400\\nAccount Summary\\nOpening\\nBalance\\nPayment/ \\nCredits\\nPurchase/ \\nDebits\\nFinance\\nCharges\\nTotal Dues\\n25,740.97 25,764.88 16,102.44 0.00 16,078.53\\nPast Dues (If any)\\nOverlimit 3 Months+ 2 Months 1 Month Current Dues\\nMinimum\\nAmount Due\\n0.00 0.00 0.00 0.00 810.00 810.00\\nIMPORTANT INFORMATION\\n1. PLEASE REVIEW THE CHANGES IN REWARD POINTS PROGRAM IN THE MITC U/S 9\\nOR CREDIT CARD PRODUCT PAGE ON BANK WEBSITE.\\nDomestic Transactions\\nDate  Transaction Description Amount (in Rs.)\\n    \\n PRADEEP PANKAJ   \\n05/05/2017 www.vodafone.in        MUMBAI 356.50  \\n05/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \\n05/05/2017 SOUTH PARK CAFE \\u0026 CANT PUNE 220.00  \\n05/05/2017 PAYTM APP              NOIDA 500.00  \\n06/05/2017 WWW OLACABS COM        GURGAON 100.00  \\n06/05/2017 BALAJI PETROLEUM       NAGPUR 511.50  \\n06/05/2017 HPCL 0.75% Cashless In MUMBAI 3.75 Cr\\n06/05/2017 PETRO SURCHARGE WAIVER 5.06 Cr\\n12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \\n12/05/2017 PAYTM APP              NOIDA 100.00  \\n12/05/2017 PAYTM APP              NOIDA 120.00  \\n12/05/2017 videocond2h_TPSL       MUMBA 500.00  \\n12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 8.00  \\n13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 300.00  \\n13/05/2017 RADHA KRISHNA PURE VEG PUNE 185.00  \\nTitanium Times Card Credit Card Statement\\n Page 1 of 4\\nDomestic Transactions\\nDate  Transaction Description Amount (in Rs.)\\n    \\n PRADEEP PANKAJ   \\n15/05/2017 PAYTM APP              NOIDA 100.00  \\n16/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \\n17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n18/05/2017 PATANJALI              PUNE 760.00  \\n18/05/2017 PAYTM APP              NOIDA 44.00  \\n18/05/2017 VITALIFE HEALTH SERVICEPUNE 1,450.00  \\n18/05/2017 ACCLAIM HEALTH CARE PV PUNE 400.00  \\n18/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \\n18/05/2017 PAYTM APP              NOIDA 100.00  \\n19/05/2017 ANIL JEWELLERS         PUNE 180.00  \\n19/05/2017 FARMERS \\u0026 CROCERS      PUNE 127.00  \\n19/05/2017 RUCHIRA WINES          PUNE 280.00  \\n19/05/2017 GETWELL CHEMIST AND DRUPUNE 585.00  \\n19/05/2017 PAYTM APP              NOIDA 100.00  \\n19/05/2017 RACHIRA WINES          PUNE 170.00  \\n19/05/2017 TELE TRANSFER CREDIT 25,740.97 Cr\\n20/05/2017 RAJMANDIR ICECREAM BANEPUNE 120.00  \\n21/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 400.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 5.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 70.00  \\n22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \\n22/05/2017 AMITA ENTERPRISES A P  PUNE 514.37  \\n22/05/2017 PAYTM APP              NOIDA 20.00  \\n22/05/2017 PETRO SURCHARGE WAIVER 5.09 Cr\\n23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n24/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 24.66  \\n24/05/2017 PAYTM APP              NOIDA 23.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 60.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \\n25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \\n26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 200.00  \\n26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 85.00  \\n27/05/2017 PAYTM APP              NOIDA 45.00  \\n27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 40.00  \\n27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \\n27/05/2017 AMITA ENTERPRISES A P  PUNE 343.33  \\n28/05/2017 MITUL ENTERPRISES PVT  MUMBAI 2,799.00  \\n28/05/2017 BURGER KING INDIA PVT LPUNE 92.58  \\n28/05/2017 PANCAKE FACTORY        PUNE 180.00  \\n29/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \\n29/05/2017 SHREE SAI ENTERPRISES  PUNE 70.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 10.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 48.00  \\n30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \\n31/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 120.00  \\n31/05/2017 AUTHENTIC AESTHETIC CENPUNE 920.00  \\n31/05/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \\n01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 130.00  \\n01/06/2017 PRABHU CATERERS        PUNE 45.00  \\n01/06/2017 MICROSOFT1291INRUSDB2C MSBILL.INFO 414.00  \\n01/06/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \\n01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \\n02/06/2017 PAYTM APP              NOIDA 15.00  \\n02/06/2017 SHELL INDIA MARKETS PV PUNE 1,011.50  \\n02/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \\n02/06/2017 PETRO SURCHARGE WAIVER 10.01 Cr\\nReward Points Summary\\nTitanium Times Card Credit Card Statement\\n Page 2 of 4\"}}";
        Assert.assertEquals(actual, result);
    }

    public final static String raw = "In case any of your personal details have changed, you can\n" +
            "update the same by contacting our Customer Service.\n" +
            " \n" +
            "Name : PRADEEP PANKAJ                          \n" +
            " \n" +
            " \n" +
            "Statement for Titanium Times Card Credit Card\n" +
            "Statement Date:05/06/2017     Card No: 5241 81XX XXXX 0280\n" +
            "Payment Due Date Total Dues Minimum Amount Due\n" +
            "25/06/2017 16,078.53 810.00\n" +
            "Credit Limit Available Credit Limit Available Cash Limit\n" +
            "1,71,000 1,53,067 68,400\n" +
            "Account Summary\n" +
            "Opening\n" +
            "Balance\n" +
            "Payment/ \n" +
            "Credits\n" +
            "Purchase/ \n" +
            "Debits\n" +
            "Finance\n" +
            "Charges\n" +
            "Total Dues\n" +
            "25,740.97 25,764.88 16,102.44 0.00 16,078.53\n" +
            "Past Dues (If any)\n" +
            "Overlimit 3 Months+ 2 Months 1 Month Current Dues\n" +
            "Minimum\n" +
            "Amount Due\n" +
            "0.00 0.00 0.00 0.00 810.00 810.00\n" +
            "IMPORTANT INFORMATION\n" +
            "1. PLEASE REVIEW THE CHANGES IN REWARD POINTS PROGRAM IN THE MITC U/S 9\n" +
            "OR CREDIT CARD PRODUCT PAGE ON BANK WEBSITE.\n" +
            "Domestic Transactions\n" +
            "Date  Transaction Description Amount (in Rs.)\n" +
            "    \n" +
            " PRADEEP PANKAJ   \n" +
            "05/05/2017 www.vodafone.in        MUMBAI 356.50  \n" +
            "05/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \n" +
            "05/05/2017 SOUTH PARK CAFE & CANT PUNE 220.00  \n" +
            "05/05/2017 PAYTM APP              NOIDA 500.00  \n" +
            "06/05/2017 WWW OLACABS COM        GURGAON 100.00  \n" +
            "06/05/2017 BALAJI PETROLEUM       NAGPUR 511.50  \n" +
            "06/05/2017 HPCL 0.75% Cashless In MUMBAI 3.75 Cr\n" +
            "06/05/2017 PETRO SURCHARGE WAIVER 5.06 Cr\n" +
            "12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \n" +
            "12/05/2017 PAYTM APP              NOIDA 100.00  \n" +
            "12/05/2017 PAYTM APP              NOIDA 120.00  \n" +
            "12/05/2017 videocond2h_TPSL       MUMBA 500.00  \n" +
            "12/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \n" +
            "13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 8.00  \n" +
            "13/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 300.00  \n" +
            "13/05/2017 RADHA KRISHNA PURE VEG PUNE 185.00  \n" +
            "Titanium Times Card Credit Card Statement\n" +
            " Page 1 of 4\n" +
            "Domestic Transactions\n" +
            "Date  Transaction Description Amount (in Rs.)\n" +
            "    \n" +
            " PRADEEP PANKAJ   \n" +
            "15/05/2017 PAYTM APP              NOIDA 100.00  \n" +
            "16/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \n" +
            "17/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "18/05/2017 PATANJALI              PUNE 760.00  \n" +
            "18/05/2017 PAYTM APP              NOIDA 44.00  \n" +
            "18/05/2017 VITALIFE HEALTH SERVICEPUNE 1,450.00  \n" +
            "18/05/2017 ACCLAIM HEALTH CARE PV PUNE 400.00  \n" +
            "18/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 30.00  \n" +
            "18/05/2017 PAYTM APP              NOIDA 100.00  \n" +
            "19/05/2017 ANIL JEWELLERS         PUNE 180.00  \n" +
            "19/05/2017 FARMERS & CROCERS      PUNE 127.00  \n" +
            "19/05/2017 RUCHIRA WINES          PUNE 280.00  \n" +
            "19/05/2017 GETWELL CHEMIST AND DRUPUNE 585.00  \n" +
            "19/05/2017 PAYTM APP              NOIDA 100.00  \n" +
            "19/05/2017 RACHIRA WINES          PUNE 170.00  \n" +
            "19/05/2017 TELE TRANSFER CREDIT 25,740.97 Cr\n" +
            "20/05/2017 RAJMANDIR ICECREAM BANEPUNE 120.00  \n" +
            "21/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 400.00  \n" +
            "22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 5.00  \n" +
            "22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 70.00  \n" +
            "22/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 33.00  \n" +
            "22/05/2017 AMITA ENTERPRISES A P  PUNE 514.37  \n" +
            "22/05/2017 PAYTM APP              NOIDA 20.00  \n" +
            "22/05/2017 PETRO SURCHARGE WAIVER 5.09 Cr\n" +
            "23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "23/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "24/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 24.66  \n" +
            "24/05/2017 PAYTM APP              NOIDA 23.00  \n" +
            "25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 60.00  \n" +
            "25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 64.00  \n" +
            "25/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \n" +
            "26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 200.00  \n" +
            "26/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 85.00  \n" +
            "27/05/2017 PAYTM APP              NOIDA 45.00  \n" +
            "27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 40.00  \n" +
            "27/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \n" +
            "27/05/2017 AMITA ENTERPRISES A P  PUNE 343.33  \n" +
            "28/05/2017 MITUL ENTERPRISES PVT  MUMBAI 2,799.00  \n" +
            "28/05/2017 BURGER KING INDIA PVT LPUNE 92.58  \n" +
            "28/05/2017 PANCAKE FACTORY        PUNE 180.00  \n" +
            "29/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 100.00  \n" +
            "29/05/2017 SHREE SAI ENTERPRISES  PUNE 70.00  \n" +
            "30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 10.00  \n" +
            "30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 48.00  \n" +
            "30/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 35.00  \n" +
            "31/05/2017 PAYTM MOBILE SOLUT INR www.paytm.in 120.00  \n" +
            "31/05/2017 AUTHENTIC AESTHETIC CENPUNE 920.00  \n" +
            "31/05/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \n" +
            "01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 130.00  \n" +
            "01/06/2017 PRABHU CATERERS        PUNE 45.00  \n" +
            "01/06/2017 MICROSOFT1291INRUSDB2C MSBILL.INFO 414.00  \n" +
            "01/06/2017 NEW PRASHANT ENTERPRIS PUNE 36.00  \n" +
            "01/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 2.00  \n" +
            "02/06/2017 PAYTM APP              NOIDA 15.00  \n" +
            "02/06/2017 SHELL INDIA MARKETS PV PUNE 1,011.50  \n" +
            "02/06/2017 PAYTM MOBILE SOLUT INR www.paytm.in 50.00  \n" +
            "02/06/2017 PETRO SURCHARGE WAIVER 10.01 Cr\n" +
            "Reward Points Summary\n" +
            "Titanium Times Card Credit Card Statement\n" +
            " Page 2 of 4";


}

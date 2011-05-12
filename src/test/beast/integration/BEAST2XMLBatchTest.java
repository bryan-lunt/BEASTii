package test.beast.integration;

import beast.core.Logger;
import beast.core.Runnable;
import beast.trace.Expectation;
import beast.trace.LogAnalyser;
import beast.util.Randomizer;
import beast.util.XMLParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * check that a chain can be resumed after termination *
 */
public class BEAST2XMLBatchTest extends TestCase {
    final static long SEED = 127;

    final static String[] XML_FILES = new String[]{"testHKY.xml", "testStrictClock.xml", "testStrictClock2.xml", "testRelaxedClock.xml",
            "testRandomLocalClock.xml", "testCoalescentUnit.xml", "testCoalescent.xml", "testCoalescent1.xml", "testTipDates.xml"};//, "testExponentialGrowth.xml", "testYuleOneSite.xml"};


    private List<Expectation> giveExpectations(int index_XML) throws Exception {
        List<Expectation> expList = new ArrayList<Expectation>();

        switch (index_XML) {
            case 0: // testHKY.xml
//        BEAST 1 testMCMC.xml
//        <expectation name="likelihood" value="-1815.75"/>
//        <expectation name="treeModel.rootHeight" value="0.0642048"/>
//        <expectation name="hky.kappa" value="32.8941"/>
                addExpIntoList(expList, "treeLikelihood", -1815.7329777865139);
                addExpIntoList(expList, "hky.kappa", 33.16730002094189);
                addExpIntoList(expList, "tree.height", 0.0642038704899834);
                break;

            case 1: // testStrictClock.xml
                addExpIntoList(expList, "treeLikelihood", -1816.8106613767247);
                addExpIntoList(expList, "tree.height", 0.06353824247135542);
                addExpIntoList(expList, "coalescent", 6.955450404612456);
                addExpIntoList(expList, "popSize", 0.13201477032492115);
                addExpIntoList(expList, "hky.kappa", 32.992630220322425);
                addExpIntoList(expList, "hky.frequencies0", 0.3265961946282029);
                addExpIntoList(expList, "hky.frequencies1", 0.2582866710781563);
                addExpIntoList(expList, "hky.frequencies2", 0.15370776098387695);
                addExpIntoList(expList, "hky.frequencies3", 0.26140937330976677);
                addExpIntoList(expList, "clockRate", 1.0);
                addExpIntoList(expList, "posterior", -1809.8552109721088);
                break;

            case 2: // testStrictClock2.xml
                addExpIntoList(expList, "treeLikelihood", -1815.6178221063008);
                addExpIntoList(expList, "tree.height", 0.0633050543439892);
                addExpIntoList(expList, "coalescent", 6.98818031803165);
                addExpIntoList(expList, "popSize", 0.1305616518299012);
                addExpIntoList(expList, "hky.kappa", 32.79479983196463);
                addExpIntoList(expList, "clockRate", 1.0);
                addExpIntoList(expList, "posterior", -1808.6296417882743);
                break;

            case 3: // testRelaxedClock.xml
                addExpIntoList(expList, "treeLikelihood", -1815.6833629399468);
                addExpIntoList(expList, "tree.height", 0.02501244505827084);
                addExpIntoList(expList, "coalescent", 12.323228234924514);
                addExpIntoList(expList, "popSize", 0.04698473959788077);
                addExpIntoList(expList, "hky.kappa", 32.17682957277128);
                addExpIntoList(expList, "S", 2.611532662341314);
                addExpIntoList(expList, "rateCategories0", 6.37037037037037);
                addExpIntoList(expList, "rateCategories1", 5.765432098765432);
                addExpIntoList(expList, "posterior", -1803.3601347050233);
                break;

            case 4: // testRandomLocalClock.xml
                addExpIntoList(expList, "treeLikelihood", -1815.9602060116856);
                addExpIntoList(expList, "tree.height", 0.07954514858114668);
                addExpIntoList(expList, "coalescent", 8.119096310087864);
                addExpIntoList(expList, "popSize", 0.11438247311835678); // todo why Hudson got 0.09581514625061524 +/- 0.007326950921469767
                addExpIntoList(expList, "hky.kappa", 32.47032863965677);
//                addExpIntoList(expList, "indicators0", 0.0);
//                addExpIntoList(expList, "indicators1", 0.012345679012345678);
                addExpIntoList(expList, "posterior", -1807.8411097015971);
                break;

            case 5: // testCoalescentUnit.xml
                addExpIntoList(expList, "tree.height", 15000.0);
                addExpIntoList(expList, "popSize", 10000.0);
//                addExpIntoList(expList, "coalescent", -30.6);
//                addExpIntoList(expList, "posterior", -30.6);
                break;

            case 6: // testCoalescent.xml
                addExpIntoList(expList, "treeLikelihood", -1816.733153794347);
                addExpIntoList(expList, "tree.height", 0.06356593871883662);
                addExpIntoList(expList, "coalescent", 6.96172944685552);
                addExpIntoList(expList, "popSize", 0.13117988457760046);
                addExpIntoList(expList, "hky.kappa", 32.85365877899093);
                addExpIntoList(expList, "hky.frequencies0", 0.3260530610729557);
                addExpIntoList(expList, "hky.frequencies1", 0.25687182798090596);
                addExpIntoList(expList, "hky.frequencies2", 0.15496948041859962);
                addExpIntoList(expList, "hky.frequencies3", 0.26210563052754);
                addExpIntoList(expList, "posterior", -1809.7714243475011);
                break;

            case 7: // testCoalescent1.xml
                addExpIntoList(expList, "treeLikelihood", -1816.733153794347);
                addExpIntoList(expList, "tree.height", 0.06356593871883662);
                addExpIntoList(expList, "coalescent", 6.96172944685552);
                addExpIntoList(expList, "popSize", 0.13117988457760046);
                addExpIntoList(expList, "hky.kappa", 32.85365877899093);
                addExpIntoList(expList, "hky.frequencies0", 0.3260530610729557);
                addExpIntoList(expList, "hky.frequencies1", 0.25687182798090596);
                addExpIntoList(expList, "hky.frequencies2", 0.15496948041859962);
                addExpIntoList(expList, "hky.frequencies3", 0.26210563052754);
                addExpIntoList(expList, "posterior", -1809.7714243475011);
                break;

            case 8: // testTipDates.xml
                addExpIntoList(expList, "treeLikelihood", -15000.435271635279);
                addExpIntoList(expList, "tree.height", 38.6109296447932);
                addExpIntoList(expList, "hky.kappa", 2179.425239015365);
                break;

//            case 8: // testExponentialGrowth.xml
////coalescent : -0.4014183147929467 +- 0.00381600199385509, expectation is 6.860816283805018
////popSize : Infinity +- 0.0, expectation is 0.13377527552728488
//                addExpIntoList(expList, "treeLikelihood", -1815.8383981132436);
//                addExpIntoList(expList, "tree.height", 0.06462423202463202);
//                addExpIntoList(expList, "coalescent", -0.4014183147929467);
//                addExpIntoList(expList, "popSize", 0.13377527552728488);
//                addExpIntoList(expList, "hky.kappa", 33.88522193182046);
//                addExpIntoList(expList, "posterior", -1816.2398164280376);
//                break;
//
//            case 9: // testYuleOneSite.xml todo XML not workign
//                addExpIntoList(expList, "treeLikelihood", -1815.498433460167);
//                addExpIntoList(expList, "tree.height", 0.06262400993502577);
//                addExpIntoList(expList, "coalescent", 6.860816283805018);
//                addExpIntoList(expList, "popSize", 0.13377527552728488);
//                addExpIntoList(expList, "hky.kappa", 34.273735441171816);
//                addExpIntoList(expList, "posterior", -1808.6376171763627);
//                break;

            default:
                throw new Exception("No such XML");
        }

        return expList;
    }


    private void addExpIntoList(List<Expectation> expList, String traceName, Double expValue) throws Exception {
        Expectation exp = new Expectation(traceName, expValue);
        expList.add(exp);
    }

    @Test
    public void testBESAT2XmlsAnalyseLog() throws Exception {

        for (int i = 0; i < 8; i++) {
            if (giveExpectations(i).size() > 0) {
                Randomizer.setSeed(SEED);
                Logger.FILE_MODE = Logger.FILE_OVERWRITE;
                String sDir = System.getProperty("user.dir");

                String sFileName = sDir + "/examples/" + XML_FILES[i];

                System.out.println("Processing " + sFileName);
                XMLParser parser = new XMLParser();
                Runnable runable = parser.parseFile(sFileName);
                runable.setStateFile("tmp.state", false);
//		   runable.setInputValue("preBurnin", 0);
//		   runable.setInputValue("chainLength", 1000);
                runable.run();

                String logFile = sDir + "/test." + SEED + ".log";
                System.out.println("\nAnalysing log " + logFile);
                LogAnalyser logAnalyser = new LogAnalyser(logFile, giveExpectations(i)); // burnIn = 0.1 * maxState

                for (Expectation expectation : logAnalyser.m_pExpectations.get()) {
                    assertTrue(XML_FILES[i] + ": Expected " + expectation.m_sTraceName.get() + " is "
                            + expectation.m_fExpValue.get() + " but got " + expectation.getTraceStatistics().getMean()
                            + " +/- " + expectation.getStdError(), expectation.isPassed());

                    assertTrue(XML_FILES[i] + ":  has very low effective sample sizes (ESS) "
                            + expectation.getTraceStatistics().getESS(), expectation.isValid());
                }

                System.out.println("\nSucceed " + sFileName);
                System.out.println("\n***************************************\n");
            }
        }
    } // testBESAT2XmlToCompareBEAST1Result

} // class ResumeTest
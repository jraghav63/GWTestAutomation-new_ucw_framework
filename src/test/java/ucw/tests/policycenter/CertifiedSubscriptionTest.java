package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.enums.Makes;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.*;
import static ucw.enums.Makes.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class CertifiedSubscriptionTest extends BaseTest {
    private PolicyCenter policyCenter;
    private static final String[] oldARCModels = {"Brera", "GT", "147", "159", "8C"};

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-2571: Modify subscription process for Certified Dealers");
        policyCenter = basePage.loginPolicyCenter();
        basePage.selectManagementType(INSURED);
    }

    @DataProvider
    public static Object[][] getMakes() {
        return new Object[][] {
                new Object[] {ARC, Spain,  ALFAROMEO},
                new Object[] {DSC, Spain,  DS       },
                new Object[] {ARC, France, ALFAROMEO},
                new Object[] {DSC, France, DS       },
                new Object[] {ARC, Italy,  ALFAROMEO},
                new Object[] {DSC, Italy,  DS       },
                new Object[] {LC,  Italy,  LANCIA   }
        };
    }

    @Test(description = "Validating that when Producer code Label = Alfa Romeo/DS/Lancia Certified then Make Typelist will have only related certified option",
        dataProvider = "getMakes")
    public void test_typelist(String label, Countries country, Makes make) {
        basePage.selectLabel(label)
                .selectCountry(country);
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        boolean result = policyCenter.atPolicyPage().makeTypeListContainsOnly(make);
        assertTrue(result, "Makes typelist contains more options than only " + make);

        if (label.equals(ARC)) { // UCWAR-3127: Remove old AlfaRomeo vehicle models for AlfaRomeoCertified dealers
            policyCenter.atPolicyPage().selectMake();
            boolean acceptedModels = policyCenter.atPolicyPage().modelSelectorNotContains(oldARCModels);
            assertTrue(acceptedModels, "Model selector may contains one of followings: Brera, GT, 147, 159, 8C");
        }
    }

    @DataProvider
    public static Object[][] getTypes() {
        return new Object[][] {
                new Object[] {Spain,  ARC, "Alfa Romeo"},
                new Object[] {Spain,  DSC, "GVO DSC"},
                new Object[] {France, ARC, "Alfa Romeo"},
                new Object[] {France, DSC, "GVO DSC"},
                new Object[] {Italy,  ARC, "Alfa Romeo"},
                new Object[] {Italy,  DSC, "Garanzia DS Certified"},
                new Object[] {Italy,  LC,  "Garanzia Lancia Certified"}
        };
    }

    @Test(description = "Validating that when Producer code Label = Alfa Romeo/DS/Lancia Certified then only related offerings should be available",
            dataProvider = "getTypes")
    public void test_certifiedOfferings(Countries country, String label, String partialText) {
        basePage.selectLabel(label)
                .selectCountry(country)
                .selectRandomCoverageType();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().inputVehicleDetailsInfo();
        policyCenter.atPolicyPage().clickCoverageTab();
        boolean result = policyCenter.atPolicyPage().coverageDropdownContainsOnly(partialText);
        assertTrue(result, "Coverage type dropdown contains more options not related to " + label);
    }
}

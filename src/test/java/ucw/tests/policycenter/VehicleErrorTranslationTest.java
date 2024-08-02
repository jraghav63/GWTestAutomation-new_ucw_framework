package ucw.tests.policycenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.Languages.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class VehicleErrorTranslationTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToPolicyCenter() {
		startTest("UCWAR-2694: add an information message to inform the dealer when the data is updated by CIN");
		basePage.selectLabel(SPOTICAR)
				.selectManagementType(INSURED);
		policyCenter = basePage.loginPolicyCenter();
		policyCenter.setLanguage(ENGLISH);
	}

	@AfterMethod
	public void revertLanguageBackToEnglish() {
		policyCenter.setLanguage(ENGLISH);
	}

	@DataProvider
	public static Object[][] getLicensePlateMessage() {
		return new String[][] {
				new String[] {FRANCE, "Veuillez rechercher les données du véhicule à l’aide de la fonction de recherche par VIN."},
				new String[] {SPAIN, "Por favor, busque los detalles del vehículo utilizando el buscador VIN"},
				new String[] {ITALY, "Per ottenere i dettagli del veicolo utilizzi per favore la funzionalità “Ricerca per VIN”"},
				new String[] {GERMANY, "Bitte suchen Sie die Fahrzeugdetails mit der FIN-Suchfunktion"},
		};
	}

	@Test(description = "If License Plate search results gives 0 result or License search is down, then \"VIN search\" should get available with an Informative message.",
		dataProvider = "getLicensePlateMessage")
	public void test_licenseSearch(String lang, String warningMessage) {
		basePage.selectRandomCountryExcept(Germany);
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.atPolicyPage().inputVehicleVin();
		policyCenter.atPolicyPage().selectVehicleType();
		policyCenter.atPolicyPage().setFirstRegistrationDate();
		policyCenter.atPolicyPage().selectMake();
		policyCenter.atPolicyPage().setModel();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().setLicensePlate();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
	}

	@DataProvider
	public static Object[][] getVINRetrieveMessage() {
		return new String[][] {
				new String[] {FRANCE, "En l'absence de résultat / ou de données partielles, veuillez vérifier/saisir manuellement les informations manquantes ci-dessous pour poursuivre la souscription"},
				new String[] {SPAIN, "Debido a que la búsqueda puede no dar resultar o resultados parciales, facilite usted manualmente la siguiente información obligatoria para continuar."},
				new String[] {ITALY, "La ricerca ha ottenuto un risultato parziale, per favore inserisca le informazioni sotto riportate manualmente per procedere."},
				new String[] {GERMANY, "Die Suche liefert nicht die erforderlichen Informationen. Um fortzufahren, überprüfen Sie bitte die folgenden notwendigen Informationen manuell."},
		};
	}

	@Test(description = "If any(/all) field details is not retrieved from VIN search results, then user should get a Informational message",
			dataProvider = "getVINRetrieveMessage")
	public void test_VINSearchRetrieval(String lang, String warningMessage) {
		basePage.selectCountry(Germany);
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().inputVin("1GCCS136358115682");
		policyCenter.atPolicyPage().clickVINSearchIcon();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
	}

	@Test(description = "In case of Partial results received from License plate or in case of No results/Partial results received from VIN search then display a warning message.",
			dataProvider = "getVINRetrieveMessage")
	public void test_VINSearchRetrieval_noResult(String lang, String warningMessage) {
		basePage.selectRandomCountryExcept(Germany);
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.atPolicyPage().setLicensePlate();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().inputVin("1GCCS136358115682");
		policyCenter.atPolicyPage().clickVINSearchIcon();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
	}

	@DataProvider
	public static Object[][] getAllVehicleDataMessage() {
		return new Object[][] {
				new Object[] {France, FRANCE, "CK239EJ", "Vos données véhicule ont été validées/mises à jour. Merci de vérifier que tous les champs obligatoires sont remplis."},
				new Object[] {Spain, SPAIN, "0259MLL", "Todos los datos han sido validados/actualizados.\nCompruebe por favor, si todos los campos obligatorios están rellenados"},
				new Object[] {Italy, ITALY, "BK905EV", "Tutti i dati dei veicoli inseriti sono stati validati/aggiornati. Per favore verifichi che tutti i campi obbligatori siano compilati."},
				new Object[] {Spain, GERMANY, "0259MLL", "Alle Fahrzeugdaten wurden validiert/aktualisiert.\nPrüfen Sie bitte, ob alle Pflichtfelder ausgefüllt sind."},
		};
	}

	@Test(description = "In case of Full results retrieved from CIN from License Plate search or VIN search then shown an information message.",
			dataProvider = "getAllVehicleDataMessage")
	public void test_licensePlate_fullResult(Countries country, String lang, String licensePlate, String warningMessage) {
		basePage.selectCountry(country)
				.selectRandomCoverageType();
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().setLicensePlate(licensePlate);
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
		policyCenter.goBackToAccSummary();
	}

	@DataProvider
	public static Object[][] getRevalidateVehicleDataMessage() {
		return new String[][] {
				new String[] {FRANCE, "Merci de valider à nouveau les données véhicule en cliquant sur la fonction de recherche"},
				new String[] {SPAIN, "Vuelva a validar los datos del vehículo haciendo clic en los iconos de búsqueda"},
				new String[] {ITALY, "Per favore è necessario rivalidare i dati del veicolo cliccando sull’icona “Cerca”."},
				new String[] {GERMANY, "Bitte überprüfen Sie die Fahrzeugdaten erneut, indem Sie auf das Suchsymbol klicken."},
		};
	}

	@Test(description = "If user returns back to the draft/quoted submission then the vehicle details returned from the car identifier " +
			"services should be retained and when user tries to navigate further to next step an error message to display",
			dataProvider = "getRevalidateVehicleDataMessage")
	public void test_returnDraftSubmission(String lang, String errorMessage) {
		basePage.selectRandomCountry()
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
		policyCenter.atPolicyPage().selectFirstType();
		policyCenter.atPolicyPage().saveDraft();
		policyCenter.goBackToAccSummary();
		policyCenter.atPolicyPage().goToMostRecentSubmissionScreen();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().clickNextButton();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, errorMessage, "Actual message is not matched with expected");
	}

	@DataProvider
	public static Object[][] getValidateLPCountryMessage() {
		return new String[][] {
				new String[] {FRANCE, "Merci de valider le pays d'immatriculation du véhicule avant de continuer la recherche en cliquant sur \uD83D\uDD0D"},
				new String[] {SPAIN, "Valide el país de la matrícula antes de continuar \uD83D\uDD0D"},
				new String[] {ITALY, "Per favore controlli il Paese di immatricolazione prima di procedere. \uD83D\uDD0D"},
		};
	}

	@Test(description = "License country should be defaulted to the country of the dealer and an alert message should be displayed to the user" +
			" when he clicks on License Plate Search: ",
			dataProvider = "getValidateLPCountryMessage")
	public void test_licensePlateCountry(String lang, String warningMessage) {
		basePage.selectRandomCountryExcept(Germany);
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().searchLicensePlate();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
	}

	@DataProvider
	public static Object[][] getMultipleVINResultMessage() {
		return new String[][] {
				new String[] {FRANCE, "La recherche par VIN retourne plusieurs résultats, merci de séléctionner le vehicule correspondant."},
				new String[] {SPAIN, "El VIN ha dado múltiples resultados. Seleccione un vehículo de la lista."},
				new String[] {ITALY, "La ricerca per VIN ha prodotto molteplici risultati. Per favore selezioni il veicolo corretto nella lista."},
				new String[] {GERMANY, "Die FIN Suche hat mehrere Ergebnisse geliefert. Bitte wählen Sie ein Fahrzeug aus der Liste aus."},
		};
	}

	@Test(description = "While querying Car Identifier if Guidewire receives multiple results of VIN, then on Vehicle screen a table will be displayed (see mockup) " +
			"for user to select the right VIN for the available option. An informative message message will be displayed on top of vehicle screen .",
			dataProvider = "getMultipleVINResultMessage")
	public void test_multipleVINResults(String lang, String warningMessage) {
		basePage.selectRandomCountry();
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.atPolicyPage().setLicensePlate();
		policyCenter.setLanguage(lang);
		policyCenter.atPolicyPage().inputVin("1NXBA02E3VZ664920");
		policyCenter.atPolicyPage().clickVINSearchIcon();
		String actualMessage = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMessage, warningMessage, "Actual message is not matched with expected");
		policyCenter.goBackToAccSummary();
	}
}
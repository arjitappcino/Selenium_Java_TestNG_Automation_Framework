package random;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RandomDataInput {

	WebDriver driver;

	Random rand = new Random();
	static Logger log = Logger.getLogger(RandomDataInput.class);
	WebDriverWait wait;
	Duration waitDuration = Duration.ofSeconds(10);

	String[] regionList = { "Archipelago", "Energy", "Food", "Gulf of Aqaba", "Logistics", "LOTL", "Mobility",
			"Nature Region", "Oxagon", "Special Events", "Spine & NICC", "The Line", "Trojena", "Water" };

	String[] sectorList = { "Food", "Energy", "Technology & Digital", "Water", "Waste", "Mobility", "Security",
			"Environment", "Hospitality" };

	String[] departmentListSurvey = { "Food", "Energy", "Technology & Digital", "Water", "Waste", "Mobility",
			"Security", "Environment", "Hospitality" };

	String[] projectTopology = { "Urban District", "District Infrastructure", "Standard Asset", "Railway/Highway",
			"Desalination/Wastewater Treatment", "Transmitter towers", "Island Development",
			"Agriculture/Horticulture", "Construction Support Network", "Metallurgy/Refinery", "Temporary Laydown",
			"Iconic/Signature Asset", "Residential Complex", "Public Art Installation", "Power Plant",
			"Roads and Pathways", "Regional Activation Program", "Resort Complex", "Bulk Food Production/Storage",
			"Resource Extraction", "Petrochemical Facility", "Industrial Complex", "Public Realm Assets",
			"Biotech/Medical/Pharmaceutical Facility", "Regional Technology Network", "Port/Marina", "Data Centre",
			"Entertainment Complex", "Aquaculture Project", "Single Event", "Other" };

	String[] projectTypes = { "MasterPlan Project", "Asset" };

	String[] categoryTypes = { "Category I", "Category II", "Category III", "Category IV",
			"Below Assessment Category" };

	public RandomDataInput(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, waitDuration);
	}

	public String getRandomRegion() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'RDIF')]")));
		return regionList[rand.nextInt(regionList.length)];
	}

	public String getRandomSector() {
		return sectorList[rand.nextInt(sectorList.length)];
	}

	public String getRandomProjectType() {
		return projectTypes[rand.nextInt(projectTypes.length)];
	}

	public String getRandomAssetTopology() {
		return projectTopology[rand.nextInt(projectTopology.length)];
	}

	public ArrayList<String> getRandomProjectTypology() {
		ArrayList<String> al = new ArrayList<String>();
		int randomNum = rand.nextInt(projectTopology.length - 1);
		int indexRandom, min = 0, max = projectTopology.length - 1;
		for (int i = 0; i < randomNum; i++) {
			indexRandom = rand.nextInt(max - min + 1) + min;
			al.add(projectTopology[indexRandom]);
		}
		return al;
	}

	public String getRandomCategoryType() {
		return categoryTypes[rand.nextInt(categoryTypes.length)];
		// return categoryTypes[2];
	}
	
	public String getRandomDepartment() {
		return departmentListSurvey[rand.nextInt(departmentListSurvey.length)];
	}

}
package ucw.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.models.testdata.CentersDataObject;
import ucw.models.testdata.LocalizationObject;
import ucw.pages.BasePage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ucw.enums.CoverageTypes.*;

public class TestDataGenerator {
    private static final Logger logger = LogManager.getLogger(TestDataGenerator.class);
    private static final ObjectMapper objMapper = new ObjectMapper();
    private static final Random rand = new Random();

    public static CentersDataObject loadDataFromFile(String fileName) {
        logger.info("Load test data from file " + fileName);
        CentersDataObject result = null;
        try {
            File dataFile = new File("src/test/resources/" + fileName);
            result = objMapper.readValue(dataFile, CentersDataObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<LocalizationObject> loadLocalizationData(String fileName) {
        logger.info("Load localization test data from file " + fileName);
        List<LocalizationObject> result = null;
        try {
            BufferedReader dataFile = new BufferedReader(new InputStreamReader(
                new FileInputStream("src/test/resources/" + fileName), StandardCharsets.ISO_8859_1));
            result = objMapper.readValue(dataFile, new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String generateRegDateFromCoverageType(String type) {
        int vehicleAge = 0;
        if (type.contains("GVO")) {
            vehicleAge = getVehicleAgeFromGVOType(type);
        } else if (type.startsWith("GW Garantie")) {
            vehicleAge = getVehicleAgeFromGWGarantieType(type);
        } else if (type.startsWith("Garanzia")) {
            vehicleAge = getVehicleAgeFromGaranziaType(type);
        }
        return generateRegDate(vehicleAge);
    }

    public static String generateRegDate(int vehicleAge) {
        int year = BasePage.systemFutureYear - vehicleAge;
        return DateHelper.getRandomDateInYearRange(year, year + 2);
    }

    public static long generateMileageFromCoverageType(String type) {
        long[] mileageRange = new long[2];
        if (type.contains("GVO")) {
            mileageRange = getMileageFromGVOType(type);
        } else if (type.startsWith("GW Garantie")) {
            mileageRange = getMileageFromGWGarantieType(type);
        } else if (type.startsWith("Garanzia")) {
            mileageRange = getMileageFromGaranziaType(type);
        }
        return rand.nextLong(mileageRange[0], mileageRange[1]);
    }

    private static long[] getMileageFromGVOType(String type) {
        long[] mileageRange = new long[2];
        switch (type) {
            case EGVOMPremium2_40, EGVOMPremium4_40, EGVOSpoticarPremium4_40 -> {
                mileageRange[0] = 10000;
                mileageRange[1] = 40000;
            }
            case GVOPremium2_60, EGVOMPremium2_60, EGVOMPremium4_60, EGVOSpoticarPremium4_60, GVOCarePremium2_60 -> {
                mileageRange[0] = 40001;
                mileageRange[1] = 60000;
            }
            case GVOARC4_80, GVOARCertfied4_80, GVODSC4_80 -> {
                mileageRange[0] = 10000;
                mileageRange[1] = 80000;
            }
            case GVOARC7_150, GVOARCertfied7_150, GVODSC7_150, EGVOSpoticarPremium7_150,
                    GVOCarePremium7_150_12M, GVOCarePremium7_150_24M -> {
                mileageRange[0] = 80001;
                mileageRange[1] = 150000;
            }
            case GVOPremium2_100, GVOPremium5_100_12, GVOPremium5_100_24, EGVOPremium2_100 -> {
                mileageRange[0] = 60001;
                mileageRange[1] = 100000;
            }
            case GVOPremium5_150, GVOPremium7_150, GVOPremium7_150_12, GVOPremium7_150_24, EGVOPremium5_150, GVOAdvanced10_150 -> {
                mileageRange[0] = 100001;
                mileageRange[1] = 150000;
            }
            case GVOCare15_200 -> {
                mileageRange[0] = 120001;
                mileageRange[1] = 200000;
            }
            case GVOPremium7_200, GVOAdvanced10_200, GVOAdvanced10_200_8, GVOAdvanced10_200_12, GVOEssential15_200_6,
                    GVOEssential15_200_12, EGVOAdvanced10_200, GVOCarePlus10_200 -> {
                mileageRange[0] = 150001;
                mileageRange[1] = 200000;
            }
        }
        return mileageRange;
    }

    private static int getVehicleAgeFromGVOType(String type) {
        int age = 0;
        switch (type) {
            case EGVOMPremium2_40, GVOPremium2_60, EGVOMPremium2_60, GVOPremium2_100, EGVOPremium2_100, GVOCarePremium2_60 -> age = 2;
            case EGVOMPremium4_40, EGVOMPremium4_60, GVOARC4_80, GVODSC4_80, GVOARCertfied4_80, EGVOSpoticarPremium4_40, EGVOSpoticarPremium4_60 -> age = 4;
            case GVOPremium5_100_12, GVOPremium5_100_24, GVOPremium5_150, EGVOPremium5_150 -> age = 5;
            case GVOPremium7_150, GVOPremium7_150_12, GVOPremium7_150_24, GVOPremium7_200, GVODSC7_150, GVOARC7_150,
                    GVOARCertfied7_150, EGVOSpoticarPremium7_150, GVOCarePremium7_150_12M, GVOCarePremium7_150_24M -> age = 7;
            case GVOAdvanced10_150, GVOAdvanced10_200, GVOAdvanced10_200_8, GVOAdvanced10_200_12, EGVOAdvanced10_200, GVOCarePlus10_200, GVOCare15_200 -> age = 9;
            case GVOEssential15_200_6, GVOEssential15_200_12 -> age = 14;
        }
        return age;
    }

    private static long[] getMileageFromGWGarantieType(String type) {
        long[] mileageRange = new long[2];
        switch (type) {
            case GWSpoticarPremium1_12M, GWSpoticarPremium1_24M -> {
                mileageRange[0] = 10000;
                mileageRange[1] = 40000;
            }
            case GWSpoticarPremium2_12M, GWSpoticarPremium2_24M -> {
                mileageRange[0] = 40001;
                mileageRange[1] = 120000;
            }
            case GWSpoticarAdvanced12M -> {
                mileageRange[0] = 150001;
                mileageRange[1] = 200000;
            }
            case GWSpoticarAdvanced24M -> {
                mileageRange[0] = 120001;
                mileageRange[1] = 150000;
            }
        }
        return mileageRange;
    }

    private static int getVehicleAgeFromGWGarantieType(String type) {
        int age = 0;
        switch (type) {
            case GWSpoticarPremium1_12M, GWSpoticarPremium1_24M -> age = 2;
            case GWSpoticarPremium2_12M, GWSpoticarPremium2_24M -> age = 6;
            case GWSpoticarAdvanced12M -> age = 11;
            case GWSpoticarAdvanced24M -> age = 7;
        }
        return age;
    }

    private static long[] getMileageFromGaranziaType(String type) {
        long[] mileageRange = new long[2];
        switch (type) {
            case GaranziaSpoticarPremium12, GaranziaSpoticarPremium24 -> {
                mileageRange[0] = 10000;
                mileageRange[1] = 100000;
            }
            case GaranziaSpoticarAdvanced12, GaranziaSpoticarAdvanced24 -> {
                mileageRange[0] = 100000;
                mileageRange[1] = 149999;
            }
            case GaranziaSpoticarEssential1_12 -> {
                mileageRange[0] = 150000;
                mileageRange[1] = 199999;
            }
            case GaranziaARC4_80_12M,GaranziaARC4_80_24M,
                    GaranziaDS4_80_24M, GaranziaLancia4_80_24M -> {
                mileageRange[0] = 10000;
                mileageRange[1] = 80000;
            }
            case GaranziaARC7_150_12M, GaranziaARC7_150_24M, GaranziaDS7_150_12M, GaranziaDS7_150_24M,
                    GaranziaLancia7_150_12M, GaranziaLancia7_150_24M -> {
                mileageRange[0] = 80001;
                mileageRange[1] = 150000;
            }
        }
        return mileageRange;
    }

    private static int getVehicleAgeFromGaranziaType(String type) {
        int age = 0;
        switch (type) {
            case GaranziaSpoticarPremium12, GaranziaSpoticarPremium24 -> age = 2;
            case GaranziaSpoticarAdvanced12, GaranziaSpoticarAdvanced24 -> age = 7;
            case GaranziaSpoticarEssential1_12 -> age = 11;
            case GaranziaARC4_80_12M, GaranziaARC4_80_24M, GaranziaDS4_80_24M,
                    GaranziaDS7_150_12M, GaranziaDS7_150_24M, GaranziaLancia4_80_24M,
                    GaranziaLancia7_150_12M, GaranziaLancia7_150_24M -> age = 3;
            case GaranziaARC7_150_12M, GaranziaARC7_150_24M -> age = 6;
        }
        return age;
    }
}

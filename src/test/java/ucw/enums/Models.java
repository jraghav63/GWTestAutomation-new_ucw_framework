package ucw.enums;

import java.util.HashMap;
import java.util.Map;

import static ucw.enums.Makes.*;

public class Models {
    public static final Map<Makes, String[]> brands = new HashMap<>();
    static {
        brands.put(ABARTH, new String[] {"695 ESSEESSE", "595 SCORPIONEORO", "595 MONSTER ENERGY YAMAHA",
                "695 70 ANNIVERSARIO", "695 RIVALE 175 ANNIVERSARY", "695 XSR YAMAHA LTD EDITION",
                "124 SPIDER 2016 EDITION", "695 BIPOSTO", "695 BIPOSTO RECORD", "695 MASERATI EDITION", "695 TRIBUTO FERRARI",
                "595 50 ANNIVERSARIO", "CABRIO ITALIA", "ZEROCENTO", "OPENING EDITION", "PUNTO SCORPIONE"});
        brands.put(ALFAROMEO, new String[] {"Giulietta", "Giulia", "Giulia Quadrifoglio", "Giulia GTA",
                "Stelvio", "Tonale"});
        brands.put(CHEVROLET, new String[] {"Crossover SUV", "Blazer", "Blazer EV", "Bolt EUV", "Captiva", "Equinox", "Equinox EV",
                "Groove", "Tracker", "Trailblazer (crossover)", "Traverse", "Trax", "SUV", "Suburban", "Tahoe",
                "Trailblazer (SUV)", "Pickup truck", "Colorado", "S10", "Colorado", "Montana", "S10 Max", "Silverado/ Cheyenne",
                "Silverado EV", "Camaro", "Corvette", "Sedan", "Cruze", "Malibu", "Monza", "Cavalier", "Onix Plus",
                "Optra", "Sail", "Aveo", "Bolt", "Cruze", "Menlo", "Onix", "MPV/ minivan", "Orlando", "Spin",
                "Van", "Express", "N300", "N400", "F Series", "N Series", "Silverado(medium duty)"});
        brands.put(CHRYSLER, new String[] {"300", "Pacifica", "Voyager / Grand Caravan", "Airflow", "Airstream", "300 (letter series)",
                "300 (non letter series)", "300M", "200", "Cirrus", "Concorde", "Conquest", "Cordoba", "Crossfire",
                "Imperial", "Imperial Parade Phaeton", "LeBaron", "LHS", "Neon", "Newport", "New Yorker", "New Yorker Fifth Avenue",
                "Prowler", "PT Cruiser", "Saratoga", "Sebring", "Shadow", "Spirit", "Sunbeam"});
        brands.put(CUPRA, new String[] {"Tavascan", "Born", "Formentor", "Leon", "Leon Sportstourer", "Ateca", "Leon E Racer", "DarkRebel"});
        brands.put(DACIA, new String[] {"Duster", "Sandero", "Logan", "Lodgy", "Dokker", "Spring (Electric)", "Jogger"});
        brands.put(DAIHATSU, new String[] {"Move", "Mira", "Copen", "Taft", "Terios", "Rocky",
                "Sirion", "Altis", "Boon", "Wake", "Tanto", "Thor", "Hijet"});
        brands.put(DODGE, new String[] {"Challenger", "Charger", "Durango", "Grand Caravan", "Journey", "Viper"});
        brands.put(FORD, new String[] {"Bronco", "Mustang", "Escape", "Explorer", "Expedition", "Edge", "Fiesta",
                "Focus", "Fusion", "Taurus", "Ranger", "F 150", "Super Duty", "Transit", "Ecosport", "Flex", "GT"});
        brands.put(HONDA, new String[] {"Accord", "Civic", "Fit", "HR V", "CR V", "Pilot", "Passport", "Odyssey", "Insight", "Clarity", "Ridgeline", "NSX"});
        brands.put(HYUNDAI, new String[] {"Accent", "Elantra", "Sonata", "Azera", "Veloster", "Kona", "Tucson", "Santa Fe", "Palisade",
                "IONIQ", "Nexo", "Venue", "Genesis G70", "Genesis G80", "Genesis G90"});
        brands.put(INFINITI, new String[] {"Q50", "Q60", "Q70", "QX30", "QX50", "QX60", "QX80"});
        brands.put(ISUZU, new String[] {"D Max", "MU X", "Reach", "Como", "Elf", "Forward", "Giga", "Traga", "Erga",
                "Erga Mio", "Gala", "Gala Mio", "Journey", "Super Cruiser", "Turquoise"});
        brands.put(IVECO, new String[] {"2007 Iveco powerstar", "2010 Iveco Winnebago", "2013 Iveco Daily", "2013 Iveco daily 4x4",
                "2014 Iveco Daily 4x4", "2017 Iveco Daily", "2017 Iveco Daily 70C", "2017 Iveco Avida", "2020 Iveco DAILY 35S13 LWB"});
        brands.put(JAGUAR, new String[] {"E Pace", "F Pace", "F Type", "I Pace", "XE", "XF"});
        brands.put(KIA, new String[] {"Soul", "Seltos", "Sportage", "Sorento", "Carnival MPV", "Telluride", "Niro", "Sportage Hybrid",
                "Niro Plug in Hybrid", "Sorento Hybrid", "Sportage Plug in Hybrid", "Niro EV", "EV6", "Sorento Plug in Hybrid",
                "Rio", "Rio 5 Door", "Forte", "K5", "Stinger", "EV9"});
        brands.put(LADA, new String[] {"Kalina Sedan", "Niva", "110", "111", "112", "Kalina Hatchback", "Nova", "Nova Combi", "Priora", "Samara 3 Doors", "Samara 5 Doors", "Samara Sedan"});
        brands.put(LANDROVER, new String[] {"RANGE ROVER", "RANGE ROVER SPORT", "RANGE ROVER VELAR", "RANGE ROVER EVOQUE", "DISCOVERY", "DISCOVERY SPORT", "NEW DEFENDER"});
        brands.put(LEXUS, new String[] {"IS", "ES", "LS", "RC", "LC", "LBX", "UX", "NX", "RX", "RZ", "TX", "CT", "IS", "HS", "ES", "GS", "LS", "SC", "RC", "LFA", "UX", "NX", "RX", "GX", "LX", "LM"});
        brands.put(MADZDA, new String[] {"MAZDA2", "MAZDA3", "MAZDA6", "CX 3", "CX 30", "CX 5", "CX 8", "CX 9", "MX 5", "MX 5 RF", "MX 30", "BT 50"});
        brands.put(MERCEDESBENZ, new String[] {"EQB SUV", "EQE Sedan", "EQE SUV", "EQS Sedan", "EQS SUV", "GLA SUV", "GLB SUV", "GLC SUV", "GLC Coupe",
                "EQB SUV", "GLE SUV", "EQE SUV", "GLE Coupe", "GLS SUV", "EQS SUV", "G Class SUV", "Mercedes Maybach GLS SUV", "A Class Sedan",
                "C Class Sedan", "E Class Sedan", "EQE Sedan", "EQS Sedan", "S Class Sedan", "Mercedes Maybach S Class", "E Class Wagon",
                "CLA Coupe", "C Class Coupe", "E Class Coupe", "CLS Coupe", "Mercedes AMG GT 4 door Coupe", "C Class Cabriolet", "E Class Cabriolet", "SL Roadster"});
        brands.put(MG, new String[] {"14/28", "14/40", "18/80", "M type Midget", "C type Midget", "D type Midget", 
                "F type Magna", "J type Midget", "K type Magnette", "L type Magna", "N type Magnette", "P type Midget", 
                "SA saloon, tourer and drop head coupe", "VA saloon, tourer and drop head coupe", "WA saloon, tourer and drop head coupe", 
                "T type Midget", "Y type", "MGA", "Midget", "MGB", "MGC", "MGB GT V8", "RV8", "F", "TF"});
        brands.put(MINI, new String[] {"ELECTRIC HARDTOP", "HARDTOP 2 DOOR", "HARDTOP 4 DOOR", "COUNTRYMAN", "COUNTRYMAN PHEV", "CLUBMAN", "JOHN COOPER WORKS", "CONVERTIBLE"});
        brands.put(MITSUBISHI, new String[] {"ECLIPSE CROSS PHEV", "ECLIPSE CROSS", "XPANDER", "OUTLANDER PHEV", "OUTLANDER", "TRITON / L200",
                "ASX/OUTLANDER SPORT/RVR", "PAJERO SPORT/ MONTERO SPORT", "Xforce", "MIRAGE / SPACE STAR", "DELICA D:5", "eK X",
                "eK WAGON", "Delica Mini", "eK SPACE", "eK X EV", "MINICAB MiEV"});
        brands.put(NISSAN, new String[] {"Nissan Ariya", "LEAF", "Sylphy", "e NV200", "EV Ambulance", "Sakura", "Note", "Kicks", "Serena", "Qashqai",
                "Skyline", "Rogue / X Trail", "DAYZ", "Roox", "Qashqai", "March / Micra", "Note", "Skyline", "Altima", "Cima", "Lannia", "Maxima",
                "Sunny / Almera / Versa", "Sylphy / Sentra", "Nissan Z / Z Proto", "GT R", "GT R50 by Italdesign", "Juke", "Kicks", "Magnite",
                "Murano", "Pathfinder", "Patrol / Armada", "Qashqai", "Rogue / X Trail", "Terra", "Terrano", "Frontier (U.S. model)",
                "Navara / Frontier / NP300", "TITAN / TITAN XD", "Elgrand", "Serena", "Sakura", "DAYZ", "Roox", "NV100 Clipper Rio",
                "NT100 Clipper", "NP200", "NV150 AD", "NV200", "NV200 Taxi", "NV250", "NV300", "NV350 Caravan", "EV Ambulance", "NV Cargo",
                "NV Passenger", "NV400", "NT400", "NT450"});
        brands.put(PORSCHE, new String[] {"718 Cayman", "718 Boxster", "718 Cayman Style Edition", "718 Boxster Style Edition", "718 Cayman S",
                "718 Boxster S", "718 Cayman GTS 4.0", "718 Boxster GTS 4.0", "718 Cayman GT4", "718 Spyder", "718 Cayman GT4 RS", "718 Spyder RS",
                "911 Carrera", "911 Carrera T", "911 Carrera Cabriolet", "911 Carrera 4", "911 Carrera 4 Cabriolet", "911 Carrera S",
                "911 Carrera S Cabriolet", "911 Carrera 4S", "911 Carrera 4S Cabriolet", "911 Targa 4", "911 Targa 4S", "911 Carrera GTS",
                "911 Carrera GTS Cabriolet", "911 Carrera 4 GTS", "911 Carrera 4 GTS Cabriolet", "911 Targa 4 GTS", "911 Edition 50 Years",
                "911 Turbo", "911 Turbo Cabriolet", "911 Turbo S", "911 Turbo S Cabriolet", "911 GT3", "911 GT3 with Touring Package",
                "911 GT3 RS", "911 Dakar", "911 S/T", "Taycan", "Taycan 4S", "Taycan GTS", "Taycan Turbo", "Taycan Turbo S", "Taycan 4 Cross Turismo",
                "Taycan 4S Cross Turismo", "Taycan Turbo Cross Turismo", "Taycan Turbo S Cross Turismo", "Taycan Sport Turismo",
                "Taycan 4S Sport Turismo", "Taycan GTS Sport", "Taycan Turbo Sport Turismo", "Taycan Turbo S Sport Turismo",
                "Panamera", "Panamera 4", "Panamera 4 Platinum", "Panamera 4 Executive", "Panamera 4 Sport Turismo",
                "Panamera 4 Sport Turismo Platinum Edition", "Panamera 4S", "Panamera 4S Executive", "Panamera 4S Sport Turismo",
                "Panamera 4 E Hybrid", "Panamera 4 E Hybrid", "Panamera 4 E Sport Turismo", "Panamera 4 E Sport Turismo", "Panamera 4S E Hybrid", "Panamera 4S E Hybrid",
                "Panamera 4S E Sport Turismo", "Panamera GTS", "Panamera GTS Sport Turismo", "Panamera Turbo S", "Panamera Turbo S Executive",
                "Panamera S Sport Turismo", "Panamera Turbo S E Hybrid", "Panamera Turbo S E Executive", "Panamera E Hybrid Turismo",
                "Macan", "Macan T", "Macan S", "Macan GTS", "Cayenne", "Cayenne E Hybrid", "Cayenne S", "Cayenne Turbo E Hybrid", "Cayenne Coupé",
                "Cayenne E Hybrid Coupé", "Cayenne S Coupé", "Cayenne Turbo E Hybrid", "Cayenne Turbo Coupé GT Package"});
        brands.put(RENAULT, new String[] {"ZOE E Tech 100% electric", "New CLIO E Tech hybrid", "CLIO 2023", "All New MEGANE E Tech",
                "CAPTUR", "New ARKANA E Tech full hybrid", "All New AUSTRAL E TECH", "TRAFIC PASSENGER"});
        brands.put(SEAT, new String[] {"Ibiza", "Arona", "Leon", "Leon Sportstourer", "Ateca", "Tarraco", "SEAT MÓ 25", "SEAT MÓ 65",
                "New SEAT MÓ 50", "SEAT MÓ 125", "New SEAT MÓ 125", "SUV Range"});
        brands.put(SKODA, new String[] {"Enyaq Coupé", "Superb iV", "Octavia iV", "Enyaq", "Superb", "Fabia", "Scala",
                "Octavia", "Kodiaq", "Kamiq", "Karoq", "Enyaq"});
        brands.put(SMART, new String[] {"forfour", "fortwo", "fortwo Cabrio", "Electric Drive",
                "1", "crossblade", "forfour Brabus", "fortwo Brabus", "fortwo Cabrio Brabus", 
                "Roadster", "Roadster Brabus", "Roadster Coupe", "Roadster Coupe Brabus"});
        brands.put(SSANGYONG, new String[] {"Rexton", "Korando", "Tivoli", "Musso", "Tivoli XLV",
                "Actyon", "Chairman", "Kyron", "Musso Sports", "Rodius"});
        brands.put(SUBARU, new String[] {"BRZ", "IMPREZA", "JUSTY", "LEGACY", "LEVORG", "WRX", "SUVs/crossovers",
                "ASCENT/EVOLTIS", "CROSSTREK", "FORESTER", "LEVORG LAYBACK", "OUTBACK", "REX", "SOLTERRA",
                "Kei cars", "CHIFFON", "PLEO", "STELLA", "SAMBAR TRUCK", "SAMBAR VAN", "SAMBAR DIAS"});
        brands.put(SUZUKI, new String[] {"Alto K10", "Baleno", "Celerio Cultus", "Ignis", "S Presso", "Swift",
                "Wagon R", "Ciaz", "Dzire/Swift Sedan", "Swace", "Across", "Brezza", "Fronx", "Grand Vitara",
                "Jimny/ Jimny Sierra", "SX4 S Cross", "Vitara/ Escudo", "Xbee", "APV", "Bolan", "Eeco",
                "Ertiga", "Invicto", "Landy", "Solio", "XL6/XL7/ Ertiga XL7",
                "Alto", "Alto Lapin", "Every Wagon", "Hustler", "Jimny (Japan, kei)", "Spacia",
                "Wagon R", "Wagon R Smile", "Ravi", "Super Carry Truck", "Carry", "Every", "Spacia Base"});
        brands.put(TOYOTA, new String[] {"Camry", "Camry Hybrid", "Corolla", "Corolla Hatchback",
                "Corolla Hybrid", "GR Supra", "Camry", "Corolla", "Corolla Hybrid", "Corolla Hatchback",
                "Prius", "Prius Prime", "Toyota Crown", "GR86", "GR Corolla", "GR Supra", "Mirai"});
        brands.put(VOLKSWAGEN, new String[] {"ID.4", "Atlas", "Atlas Cross Sport", "Tiguan", "Taos",
                "Jetta", "Jetta GLI", "Arteon", "Golf R", "Golf GTI", "Sedans", "SUVs", "Compacts",
                "Electric Vehicles", "ID. Buzz", "ID. 7"});
        brands.put(VOLVO, new String[] {"C40 Recharge Pure Electric", "XC40 Recharge Pure Electric",
                "EX30 Pure Electric", "XC90 Recharge", "XC60 Recharge", "XC90", "XC60", "XC40", "V60", "S60"});
        brands.put(BMW, new String[] {"i7", "iX3", "i4", "X3", "X4", "X5", "X6", "The NEW X7",
                "8 Series Gran Coupe", "The NEW 7", "5 Series", "4 Series Convertible", "4 Series Gran Coupe",
                "The NEW 3", "The New Z4"});
        brands.put(AUDI, new String[] {"R8", "R8 Spyder", "R8 Heritage", "TT Coupe", "TTS Coupe", "TT Roadster",
                "80 quattro", "A5 Cabriolet", "S5 Cabriolet", "A3", "A4", "Q4 e tron", "Q4 Sportback e tron",
                "RS", "Q8", "SQ8", "Q3", "Q5", "Q5 Sportback", "SQ5", "Q7", "SQ7", "S3", "RS 5 Coupe"});
        brands.put(DS, new String[] {"DS3", "DS4", "DS5", "DS7", "DS9"});
        brands.put(PEUGEOT, new String[] {"208", "508", "Traveller", "2008", "SUV 3008", "3008 Hybrid",
                "Rifter", "Partner", "Boxer"});
        brands.put(RAM, new String[] {"1500", "1500 Classic", "2500", "3500", "3500 Chassis Cab",
                "4500 Chassis Cab", "5500 Chassis Cab", "ProMaster City", "ProMaster"});
        brands.put(FIAT, new String[] {"500", "500c", "Panda", "500X", "124 Spider", "500e",
                "500L", "Doblo", "124 Sport Spider"});
        brands.put(JEEP, new String[] {"GRAND CHEROKEE", "GRAND CHEROKEE 4xe", "RENEGADE",
                "WRANGLER", "COMPASS", "GLADIATOR", "WRANGLER 4xe"});
        brands.put(CITROEN, new String[] {"AMI", "C3", "C3 Aircross", "C4 & Ë C4 ELECTRIC",
                "NEW Ë C4 X ELECTRIC", "C5 X & C5 X HYBRID", "NEW Ë BERLINGO ELECTRIC",
                "Ë SPACETOURER ELECTRIC", "CITROËN OLI [ALL Ë]"});
        brands.put(OPEL, new String[] {"Astra", "Agila", "Corsa", "Calibra", "Astra G",
                "Manta", "Admiral", "Olympia", "Meriva", "Zafira",
                "Insignia", "Signum", "Movano", "Commodore", "Laubfrosch"});
        brands.put(LANCIA, new String[] {"Ypsilon"});
    }
}

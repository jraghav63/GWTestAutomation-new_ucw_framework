package ucw.enums;

public enum Makes {
    ABARTH("Abarth"),
    ALFAROMEO("Alfa Roméo"),
    BMW("BMW"),
    AUDI("Audi"),
    CHEVROLET("Chevrolet"),
    CHRYSLER("Chrysler"),
    CITROEN("Citroën"),
    CUPRA("Cupra"),
    DACIA("Dacia"),
    DAIHATSU("Daihatsu"),
    DODGE("Dodge"),
    DS("DS"),
    FIAT("Fiat"),
    FORD("Ford"),
    HONDA("Honda"),
    HYUNDAI("Hyundai"),
    INFINITI("Infiniti"),
    ISUZU("Isuzu"),
    IVECO("Iveco"),
    JAGUAR("Jaguar"),
    JEEP("Jeep"),
    KIA("Kia"),
    LADA("Lada"),
    LANCIA("Lancia"),
    LANDROVER("Land Rover"),
    LEXUS("Lexus"),
    MADZDA("Mazda"),
    MERCEDESBENZ("Mercedes-Benz"),
    MG("MG"),
    MINI("Mini"),
    MITSUBISHI("Mitsubishi"),
    NISSAN("Nissan"),
    OPEL("Opel"),
    PEUGEOT("Peugeot"),
    PORSCHE("Porsche"),
    RAM("RAM"),
    RENAULT("Renault"),
    SEAT("Seat"),
    SKODA("Skoda"),
    SMART("Smart"),
    SSANGYONG("SsangYong"),
    SUBARU("Subaru"),
    SUZUKI("Suzuki"),
    TOYOTA("Toyota"),
    VOLKSWAGEN("Volkswagen"),
    VOLVO("Volvo");

    private String make;
    Makes(final String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return make;
    }
}

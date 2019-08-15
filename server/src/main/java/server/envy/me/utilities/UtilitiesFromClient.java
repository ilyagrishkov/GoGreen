package server.envy.me.utilities;

import java.util.Date;

public class UtilitiesFromClient {
    private int electricityBill;

    private Date datetime;

    private int cleanEnergyPercentage;

    private int watersewage;

    private int natgas;

    private int roomTemp;

    private boolean solarPanels;

    /**
     * Utilities from Client class.
     *
     * @param electricityBill Electricity bill price.
     * @param datetime Basic date
     * @param cleanEnergyPercentage Percentage of clean energy
     * @param watersewage Price for water and sewage
     * @param natgas Price for natural gas
     * @param roomTemp Price for room temp
     * @param solarPanels Is there solar panels
     */
    public UtilitiesFromClient(int electricityBill, Date datetime,
                               int cleanEnergyPercentage, int watersewage,
                               int natgas, int roomTemp, boolean solarPanels) {
        this.electricityBill = electricityBill;
        this.datetime = datetime;
        this.cleanEnergyPercentage = cleanEnergyPercentage;
        this.watersewage = watersewage;
        this.natgas = natgas;
        this.roomTemp = roomTemp;
        this.solarPanels = solarPanels;
    }

    public UtilitiesFromClient() {}

    public int getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(int electricityBill) {
        this.electricityBill = electricityBill;
    }

    public Date getDate() {
        return datetime;
    }

    public void setDate(Date datetime) {
        this.datetime = datetime;
    }

    public int getCleanEnergyPercentage() {
        return cleanEnergyPercentage;
    }

    public void setCleanEnergyPercentage(int cleanEnergyPercentage) {
        this.cleanEnergyPercentage = cleanEnergyPercentage;
    }

    public int getWatersewage() {
        return watersewage;
    }

    public void setWatersewage(int watersewage) {
        this.watersewage = watersewage;
    }

    public int getNatgas() {
        return natgas;
    }

    public void setNatgas(int natgas) {
        this.natgas = natgas;
    }

    public int getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
    }

    public boolean isSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(boolean solarPanels) {
        this.solarPanels = solarPanels;
    }
}

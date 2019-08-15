package server.api.connection;

public class UtilitiesData {

    int squarefeet;
    int elecBill;
    int gasBill;
    int watersewage;
    int cleanpercent;
    double priceGasoline;
    double priceNatGas;

    /**
     * Constructor for UtilitiesData.
     * @param squarefeet - size of a house
     * @param elecBill - electric bill of last month
     * @param gasBill - natural gas bill of last month
     * @param watersewage - water and sewage of last month
     * @param cleanpercent - % electricity from RECs or offsets
     * @param priceGasoline - price of gasoline
     * @param priceNatGas - price of natural gas
     */
    public UtilitiesData(int squarefeet, int elecBill, int gasBill, int watersewage,
                     int cleanpercent, double priceGasoline, double priceNatGas) {
        this.squarefeet = squarefeet;
        this.elecBill = elecBill;
        this.gasBill = gasBill;
        this.watersewage = watersewage;
        this.cleanpercent = cleanpercent;
        this.priceGasoline = priceGasoline;
        this.priceNatGas = priceNatGas;
    }
}

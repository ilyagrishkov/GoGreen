package server.envy.me.settings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class SettingsClass {

    @Id
    @Column(name = "userid")
    private int userId;

    @Column(name = "gasprice")
    private double gasPrice;

    @Column(name = "electricityprice")
    private double electricityPrice;

    @Column(name = "electricityemmfactor")
    private double electricityEmmFactor;

    @Column(name = "natgasprice")
    private double natGasPrice;

    @Column(name = "livingspace")
    private int livingSpace;

    /**
     * Constructor.
     * @param userId of the user the settings correspond to
     * @param gasPrice price of gas
     * @param electricityPrice price of electricity
     * @param electricityEmmFactor emission factor
     * @param natGasPrice price of natural gas
     * @param livingSpace living space of main habitation
     */
    public SettingsClass(int userId,
                         double gasPrice,
                         double electricityPrice,
                         double electricityEmmFactor,
                         double natGasPrice,
                         int livingSpace) {
        this.userId = userId;
        this.gasPrice = gasPrice;
        this.electricityPrice = electricityPrice;
        this.electricityEmmFactor = electricityEmmFactor;
        this.natGasPrice = natGasPrice;
        this.livingSpace = livingSpace;
    }

    public SettingsClass() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public double getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public double getElectricityEmmFactor() {
        return electricityEmmFactor;
    }

    public void setElectricityEmmFactor(double electricityEmmFactor) {
        this.electricityEmmFactor = electricityEmmFactor;
    }

    public double getNatGasPrice() {
        return natGasPrice;
    }

    public void setNatGasPrice(double natGasPrice) {
        this.natGasPrice = natGasPrice;
    }

    public int getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(int livingSpace) {
        this.livingSpace = livingSpace;
    }
}

public class VIN {

    private String vin;
    private int[] prime = {17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89};
    public VIN(){
        vin = "";
    }

    public VIN(String id){
        this.vin = id;
    }

    public String getVIN() {
        return vin;
    }

    public void setVIN(String vin) {
        this.vin = vin;
    }

    @Override
    public boolean equals(Object obj) {
        return this.vin.equals(((VIN) obj).getVIN());
    }

    @Override
    public int hashCode() {
        int number = 0;
        for (int i = 0; i < vin.length(); i++) {
            if((i % 2) == 0){
                number +=  vin.charAt(i) * Math.pow(37, vin.length() - i - 1);
            }
            else {
                number -=  vin.charAt(i) * Math.pow(17, vin.length() - i - 1);
            }
        }

        return Math.abs(number);
    }

    public static void main(String[] args) {
        VIN vin = new VIN("RFLUT78MOVE962909");
        System.out.println(vin.hashCode());

    }
}

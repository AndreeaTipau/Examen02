package ro.pub.cs.systems.pdsd.practicaltest02;

public class Information {
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;

    public  Information() {
        this.data1   = null;
        this.data2   = null;
        this.data3   = null;
        this.data4   = null;
        this.data5   = null;
    }

    public  Information(String data1,String data2,String data3,String data4,String data5) {
        this.data1   = data1;
        this.data2   = data2;
        this.data3   = data3;
        this.data4   = data4;
        this.data5   = data5;
    }

    public void setData1(String data) {
        this.data1 = data;
    }

    public String getData1() {
        return data1;
    }
    
    public void setData2(String data) {
        this.data2 = data;
    }

    public String getData2() {
        return data2;
    }
    
    public void setData3(String data) {
        this.data3 = data;
    }

    public String getData3() {
        return data3;
    }
    
    public void setData4(String data) {
        this.data4 = data;
    }

    public String getData4() {
        return data4;
    }
    
    public void setData5(String data) {
        this.data5 = data;
    }

    public String getData5() {
        return data5;
    }


    

    @Override
    public String toString() {
        return Constants.DATA1 + ": " + data1 + "\n\r" +
                Constants.DATA2 + ": " + data2 + "\n\r" +
                Constants.DATA3 + ": "+ data3 + "\n\r" +
                Constants.DATA4 + ": "+ data4 + "\n\r" +
                Constants.DATA5 + ": " + data5;
    }

}

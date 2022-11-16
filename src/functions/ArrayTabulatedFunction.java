package functions;


public class ArrayTabulatedFunction implements TabulatedFunction{
    private FunctionPoint[] ValuesArray;
    private int AvailableNumberOfPoints;

    public void print(){
        for(int i = 0; i < AvailableNumberOfPoints; i++){
            System.out.println("( " + ValuesArray[i].getX() + " ; " + ValuesArray[i].getY() + " )");
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException, IllegalArgumentException{
        if(leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException();
        }else{

            double size = ((rightX-leftX)/(pointsCount));
            this.ValuesArray = new FunctionPoint[pointsCount];

            for (int i=0;i<pointsCount;i++){
                this.ValuesArray[i] = new FunctionPoint(leftX+i*size,0);
            }

            AvailableNumberOfPoints = pointsCount;
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException, IllegalArgumentException{
        if(leftX >= rightX || values.length < 2){
            throw new IllegalArgumentException();
        }else {
            this.ValuesArray = new FunctionPoint[values.length];
            double size = ((rightX - leftX) / (values.length));

            for (int i = 0; i < values.length; i++) {
                this.ValuesArray[i] = new FunctionPoint(leftX + i * size, values[i]);
            }

            AvailableNumberOfPoints = values.length;
        }
    }

    public double getLeftDomainBorder(){
        return this.ValuesArray[0].getX();
    }

    public double getRightDomainBorder(){
        return this.ValuesArray[AvailableNumberOfPoints-1].getX();
    }

    private boolean checkBorders(FunctionPoint point){
        return (!((point.getX() > getRightDomainBorder()) || (point.getX() < getLeftDomainBorder())));
    }

    private boolean checkBorders(double x){
        return (!((x > getRightDomainBorder()) || (x < getLeftDomainBorder())));
    }

    public double getFunctionValue(double x){
        if((this.ValuesArray[0].getX() > x ) || (this.ValuesArray[ValuesArray.length-1].getX() < x)){
            return Double.NaN;
        }
        int i = 0;
        double x1,x2,y1,y2;

        //4 points for calculations
        x1 = ValuesArray[AvailableNumberOfPoints-2].getX();
        y1 = ValuesArray[AvailableNumberOfPoints-2].getY();
        x2 = ValuesArray[AvailableNumberOfPoints-1].getX();
        y2 = ValuesArray[AvailableNumberOfPoints-1].getY();
        //find k
        double k = (y2 - y1)/(x2 - x1);
        //find b
        double b = y1 - k * x1;

        return k * x + b;
    }

    public int getPointsCount(){
        return AvailableNumberOfPoints;
    }

    @Override
    public void setPointsCount(int count) {

    }

    public FunctionPoint getPoint(int index){
        if(index < AvailableNumberOfPoints && index >= 0){
            return new FunctionPoint(this.ValuesArray[index]);
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(AvailableNumberOfPoints == 1 && index >=0){
            this.ValuesArray[index] = point;
        }
        else if(index >=0){
            if(index < AvailableNumberOfPoints){
                if(index == 0 && point.getX() < ValuesArray[1].getX()){
                    this.ValuesArray[index] = point;
                }
                else if (index == AvailableNumberOfPoints - 1 && point.getX() > ValuesArray[index-1].getX()){
                    this.ValuesArray[index] = point;
                }
                else if (index > 0 && index < AvailableNumberOfPoints-1  && point.getX() > ValuesArray[index-1].getX() && point.getX() < ValuesArray[index+1].getX()) {
                    this.ValuesArray[index] = point;
                } else throw new InappropriateFunctionPointException();
            } else throw new FunctionPointIndexOutOfBoundsException();
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public double getPointX(int index){
        if(index < AvailableNumberOfPoints && index >= 0){
            return this.ValuesArray[index].getX();
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if(AvailableNumberOfPoints == 1 && index >=0){
            this.ValuesArray[index].setX(x);
        }
        else if(index >=0){
            if(index < AvailableNumberOfPoints){
                if(index == 0 && x < ValuesArray[1].getX()){
                    this.ValuesArray[index].setX(x);
                }
                else if (index == AvailableNumberOfPoints - 1 && x > ValuesArray[index-1].getX()){
                    this.ValuesArray[index].setX(x);
                }
                else if (index > 0 && index < AvailableNumberOfPoints-1  && x > ValuesArray[index-1].getX() && x < ValuesArray[index+1].getX()) {
                    this.ValuesArray[index].setX(x);
                } else throw new InappropriateFunctionPointException();
            } else throw new FunctionPointIndexOutOfBoundsException();
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public double getPointY(int index){
        if(index < AvailableNumberOfPoints && index >= 0){
            return this.ValuesArray[index].getY();
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public void setPointY(int index, double y){
        if(AvailableNumberOfPoints == 1 && index >=0){
            this.ValuesArray[index].setY(y);
        }
        else if(index >=0){
            if(index < AvailableNumberOfPoints){
                if(index == 0 && y < ValuesArray[1].getX()){
                    this.ValuesArray[index].setY(y);
                }
                else if (index == AvailableNumberOfPoints - 1 && y > ValuesArray[index-1].getX()){
                    this.ValuesArray[index].setY(y);
                }
                else if (index > 0 && index < AvailableNumberOfPoints-1  && y > ValuesArray[index-1].getX() && y < ValuesArray[index+1].getX()) {
                    this.ValuesArray[index].setY(y);
                }
            }
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public void deletePoint(int index){
        if(index < getPointsCount() && index >= 0){
            System.arraycopy(ValuesArray, index + 1, ValuesArray, index, ValuesArray.length - index - 1);
            AvailableNumberOfPoints--;
        }
        else throw new FunctionPointIndexOutOfBoundsException();
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (AvailableNumberOfPoints < 3) {
            throw new IllegalStateException();
        } else {
            int index = 0;
            if (point.getX() > this.ValuesArray[AvailableNumberOfPoints - 1].getX()) {
                index = AvailableNumberOfPoints;
                if (this.ValuesArray.length > AvailableNumberOfPoints) {
                    AvailableNumberOfPoints++;
                }
            } else {
                while (ValuesArray[index].getX() < point.getX()) {
                    index++;
                }
                if (point.getX() == ValuesArray[index].getX()) {
                    throw new InappropriateFunctionPointException();
                }
            }

            if (index < AvailableNumberOfPoints) {
                if (AvailableNumberOfPoints <= this.ValuesArray.length) {
                    FunctionPoint[] tmp = new FunctionPoint[getPointsCount()];
                    System.arraycopy(ValuesArray, 0, tmp, 0, tmp.length);
                    this.ValuesArray = new FunctionPoint[getPointsCount() + 1];
                    AvailableNumberOfPoints++;
                    System.arraycopy(tmp, 0, ValuesArray, 0, index);
                    this.ValuesArray[index] = point;
                    System.arraycopy(tmp, index, ValuesArray, index + 1, tmp.length - index);
                } else {
                    System.arraycopy(ValuesArray, index, ValuesArray, index + 1, getPointsCount() - index);
                    this.ValuesArray[index] = point;
                }

            } else {
                FunctionPoint[] tmp = new FunctionPoint[getPointsCount()];
                System.arraycopy(ValuesArray, 0, tmp, 0, tmp.length);
                this.ValuesArray = new FunctionPoint[getPointsCount() + 1];
                AvailableNumberOfPoints++;
                System.arraycopy(tmp, 0, ValuesArray, 0, tmp.length);
                this.ValuesArray[AvailableNumberOfPoints - 1] = point;

            }
        }
    }
}
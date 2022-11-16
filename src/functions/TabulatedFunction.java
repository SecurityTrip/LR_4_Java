package functions;

public interface TabulatedFunction {

    void print();

    int getPointsCount();
    void setPointsCount(int count);

    double getLeftDomainBorder();
    double getRightDomainBorder();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;
    void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;
    void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointY(int index) throws FunctionPointIndexOutOfBoundsException;
    void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException;

    double getFunctionValue(double x);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
    void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException;


}

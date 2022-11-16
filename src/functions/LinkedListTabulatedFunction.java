package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction{

    private static class FunctionNode {

        private FunctionPoint point;
        private FunctionNode prev;
        private FunctionNode next;

    }

    private int AvailableNumberOfPoints = 0;
    private final FunctionNode head;

    public LinkedListTabulatedFunction(double leftX, double rightX, int AvailableNumberOfPoints) throws IllegalArgumentException, InappropriateFunctionPointException {
        this(leftX, rightX, new double[AvailableNumberOfPoints]);
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException, InappropriateFunctionPointException {

        if (leftX >= rightX)
            throw new IllegalArgumentException();

        if (values.length < 2)
            throw new IllegalArgumentException();

        head = new FunctionNode();
        head.prev = head;
        head.next = head;

        double size = ((rightX - leftX) / (values.length));
        for (int index = 0; index < values.length; ++index)
            addPoint(new FunctionPoint(leftX + size * index, values[index]));

    }

    protected FunctionNode getNodeByIndex(int index) {

        FunctionNode current = head;
        while (index-- >= 0) current = current.next;
        return current;

    }

    protected FunctionNode addNodeToTail() {

        FunctionNode node = new FunctionNode();
        node.next = head;
        node.prev = head.prev;

        head.prev.next = node;
        head.prev = node;

        setPointsCount(getPointsCount() + 1);
        return node;

    }

    protected FunctionNode addNodeByIndex(int index) {

        FunctionNode node = new FunctionNode();
        FunctionNode current = getNodeByIndex(index);

        node.next = current;
        node.prev = current.prev;

        current.prev.next = node;
        current.prev = node;

        setPointsCount(getPointsCount() + 1);
        return node;

    }

    protected FunctionNode insertAfterNode(FunctionNode current) {

        FunctionNode node = new FunctionNode();

        node.prev = current;
        node.next = current.next;

        current.next.prev = node;
        current.next = node;

        setPointsCount(getPointsCount() + 1);
        return node;

    }

    protected FunctionNode deleteNodeByIndex(int index) {

        FunctionNode node = getNodeByIndex(index);

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = null;
        node.next = null;

        setPointsCount(getPointsCount() - 1);
        return node;

    }

    public void print() {

        FunctionNode node = head;
        System.out.println();

        while ((node = node.next) != head)
            System.out.println("( " + node.point.getX() + " ; " + node.point.getY() + " )");

    }

    public int getPointsCount() {
        return AvailableNumberOfPoints;
    }

    public void setPointsCount(int count) {
        AvailableNumberOfPoints = count;
    }

    public double getLeftDomainBorder() {
        return head.next.point.getX();
    }

    public double getRightDomainBorder() {
        return head.prev.point.getX();
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        return getNodeByIndex(index).point;

    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        FunctionNode node = getNodeByIndex(index);
        if (!isClamped(node, point.getX()))
            throw new InappropriateFunctionPointException();

        node.point = point;

    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        return getPoint(index).getX();

    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        FunctionNode node = getNodeByIndex(index);
        if (!isClamped(node, x))
            throw new InappropriateFunctionPointException();

        node.point.setX(x);

    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        return getPoint(index).getY();

    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        getPoint(index).setY(y);

    }

    public double getFunctionValue(double x) {

        if (isOutOfBounds(x))
            return Double.NaN;

        FunctionNode node = head;
        while ((node = node.next) != head) {

            if (node.point.getX() == x)
                return node.point.getY();

            if (x >= node.point.getX() && x <= node.next.point.getX()) {

                FunctionPoint left = node.point;
                FunctionPoint right = node.next.point;

                double k = (right.getY() - left.getY()) / (right.getX() - left.getX());
                double b = right.getY() - k * right.getX();
                return k * x + b;

            }

        }

        return Double.NaN;

    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {

        FunctionNode position = head;
        FunctionNode current = head;

        while ((current = current.prev) != head) {

            if (point.getX() == current.point.getX())
                throw new InappropriateFunctionPointException();

            if (point.getX() > current.point.getX()) {

                position = current;
                break;

            }

        }

        insertAfterNode(position).point = point;

    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException {

        if (!isCorrectPosition(index))
            throw new FunctionPointIndexOutOfBoundsException();

        if (getPointsCount() <= 2)
            throw new IllegalStateException();

        setPointsCount(getPointsCount() - 1);
        deleteNodeByIndex(index);

    }

    protected boolean isCorrectPosition(int index) {
        return index >= 0 && index < getPointsCount();
    }

    protected boolean isClamped(FunctionNode node, double x) {
        double leftX = node.prev == head ? getLeftDomainBorder() : node.prev.point.getX();
        double rightX = node.next == head ? getRightDomainBorder() : node.next.point.getX();
        return x > leftX && x < rightX;
    }

    protected boolean isOutOfBounds(double x) {
        return x < getLeftDomainBorder() || x > getRightDomainBorder();
    }

}

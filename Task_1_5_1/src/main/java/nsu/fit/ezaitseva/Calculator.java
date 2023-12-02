package nsu.fit.ezaitseva;

import java.util.Stack;


/**
 * class describing calculator with funcs +, -, *, /,
 * sin, cos, pow, log, sqrt
 */
public class Calculator {
    Stack<Double> stack = new Stack<>();


    /**
     * get upper element from stack
     * @return upper element
     * @throws WrongPolishNotationException
     */
    public Double getUpperElement() throws WrongPolishNotationException {
        try {
            Double elem = stack.pop().doubleValue();
            return elem;
        }
        catch (Exception error) {
            throw new WrongPolishNotationException("Wrong Polish Notation");
        }
    }


    /**
     * get two upper elements from stack
     * @return two upper elements
     * @throws WrongPolishNotationException
     */
    public Double[] getTwoUpperElement() throws WrongPolishNotationException {
        try {
            Double[] twoElems = new Double[2];
            twoElems[0] = stack.pop().doubleValue();
            twoElems[1] = stack.pop().doubleValue();
            return twoElems;
        }
        catch (Exception error) {
            throw new WrongPolishNotationException("Wrong Polish Notation");
        }
    }

    /**
     *
     * @param inputData - input value in Polish Notation
     * @return result of calculating
     * @throws WrongPolishNotationException
     * @throws NoSuchFunctionException
     */
    public Double calculateResult(String inputData) throws WrongPolishNotationException, NoSuchFunctionException {
        String[] elems = inputData.split(" ");
        Double[] nums;
        for (int i = elems.length - 1; i >= 0; i--) {
            switch (elems[i]) {
                case "+":
                    nums = getTwoUpperElement();
                    stack.push(nums[0] + nums[1]);
                    break;

                case "-":
                    nums = getTwoUpperElement();
                    stack.push(nums[0] - nums[1]);
                    break;

                case "*":
                    nums = getTwoUpperElement();
                    stack.push(nums[0] * nums[1]);
                    break;

                case "/":
                    nums = getTwoUpperElement();
                    stack.push(nums[0] / nums[1]);
                    break;

                case "log":
                    stack.push(Math.log(getUpperElement()));
                    break;

                case "pow":
                    nums = getTwoUpperElement();
                    stack.push(Math.pow(nums[0], nums[1]));
                    break;

                case "sqrt":
                    stack.push(Math.sqrt(getUpperElement()));
                    break;

                case "sin":
                    stack.push(Math.sin(getUpperElement()));
                    break;

                case "cos":
                    stack.push(Math.cos(getUpperElement()));
                    break;

                default:
                    try {
                        stack.push(Double.parseDouble(elems[i]));
                    } catch (Exception ex) {
                        throw new NoSuchFunctionException("No such function");
                    }

            }
        }
        Double result = getUpperElement();
        if (!stack.isEmpty()) {
            throw new WrongPolishNotationException("Wrong Polish Notation");
        }
        return result;
    }

}

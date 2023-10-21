package algorithm;

import java.util.Stack;

public class MinStack {

    Stack<Integer> stack;
    Stack<Integer> stack_min;

    public MinStack() {
        stack = new Stack<>();
        stack_min = new Stack<>();
    }

    public void push(int val) {
        stack.add(val);
        if (stack_min.isEmpty() || stack_min.peek() >= val)
            stack_min.add(val);
    }

    public void pop() {
        Integer pop = stack.pop();
        /**
         * !!! integer 的比较要用 equals() ，不能用 ==
         */
        if (stack_min.peek().equals(pop))
            stack_min.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return stack_min.peek();
    }
}

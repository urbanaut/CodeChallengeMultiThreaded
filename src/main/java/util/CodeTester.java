package util;

import org.testng.annotations.Test;

import java.util.Stack;

public class CodeTester {

    // TODO: Move this class to it's own project for testing code

    private static Stack<Integer> toCrawlList = new Stack<Integer>();

    @Test
    public static void queueTest() {
        System.out.println("Initial Queue size: " + toCrawlList.size());
        for (int i = 0; i < 10; i++) {
            toCrawlList.push(i);
        }
        for (int number : toCrawlList) {
            System.out.println("Queue index: " + number);
        }
        int queueSize = toCrawlList.size();
        System.out.println("Queue size before: " + queueSize);
        for (int y = 0; y < queueSize; y++) {
            System.out.println("y: " + y);
            System.out.println("List size:" + toCrawlList.size());
            toCrawlList.pop();
        }
        System.out.println("Queue size after: " + toCrawlList.size());
    }

    @Test
    public void StairCase() {
        int n = 10;
        for(int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if ((i+j) > n)
                    System.out.print("#");
                else if ((i+j) == n)
                    System.out.print(" ");
                else
                    System.out.print("");
            }
            System.out.println();
        }
    }

}


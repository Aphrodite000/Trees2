import java.util.Scanner;

class Node {
    char val;
    Node left;
    Node right;
}

class ReturnValue {
    Node root;
    int used;
}
public class Main {
    private static ReturnValue buildTree(char[] preorder) {
        return null;
    }
    //中序遍历
    private static void inorderTraversal(Node root) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.left);
        System.out.print(root.val + " ");
        inorderTraversal(root.right);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String preorderLine = scanner.nextLine();
        char[] preOrder = preorderLine.toCharArray();
        ReturnValue rv = buildTree(preOrder);
        inorderTraversal(rv.root);
    }
}


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Trees {
    //给中序和后序，还原二叉树
    public static TreeNode buildTree(List<Integer> inOrder, List<Integer> postOrder){
        if(inOrder.size()==0)
            return null ;
        //跟结点的值
        int rootValue=postOrder.get(postOrder.size()-1);
        //根据根节点的值在中序中判断左子树的结点个数
        int leftCount=inOrder.indexOf(rootValue);
        //把中序,后序的左子树分割出来
        List<Integer> leftInorder=inOrder.subList(0,leftCount);
        List<Integer> leftPostOrder=postOrder.subList(0,leftCount);
        //把中序，后序的右子树分割出来
        List<Integer> rightInOrder=inOrder.subList(leftCount+1,inOrder.size());
        //后序的根节点已经算过了
        List<Integer> rightPostOrder=postOrder.subList(leftCount,inOrder.size()-1);

        TreeNode root=new TreeNode();
        root.val=rootValue;
        root.left=buildTree(leftInorder,leftPostOrder);
        root.right=buildTree(rightInOrder,rightPostOrder);

        return root;
    }
    //给你前序和中序，还原二叉树
    public static TreeNode buildTree2(List<Integer> perOrder,List<Integer> inOrder){
        if(perOrder.size()==0){
            return null;
        }
        int rootValue=perOrder.get(0);
        //leftCount是根结点的下标
        int leftCount=inOrder.indexOf(rootValue);

        List<Integer> leftPerOrder=perOrder.subList(1,1+leftCount);
        List<Integer> leftInOrder=inOrder.subList(0,leftCount);
        List<Integer> rightPerOrder=perOrder.subList(leftCount+1,perOrder.size());
        List<Integer> rightInOrder=inOrder.subList(leftCount+1,inOrder.size());

        TreeNode root=new TreeNode();
        root.val=rootValue;
        //再去还原它的左右子树的二叉树
        root.left=buildTree2(leftInOrder,leftPerOrder);
        root.right=buildTree2(leftInOrder,leftPerOrder);

        return root;
    }
    //给你后序，中序，用数组形式还原二叉树
    public static TreeNode buildTreeArray(int[] inOrder,int[] postOrder){
        if(inOrder.length==0){
            return null;
        }
        //找出根节点
        int rootValue=postOrder[postOrder.length-1];
        //先给leftCount一个值，，然后通过遍历去找具体的值
        int leftCount=-1;
        for(int i=0;i<inOrder.length;i++){
            if(inOrder[i]==rootValue){
                leftCount=i;
                //找到根节点的下标刚好也是左结点的个数
            }
        }
        //List用引用.sublist截取[  ),数组用Arrays.copyOfRange(引用，下限，上限)
        int[] leftInOrder= Arrays.copyOfRange(inOrder, 0, leftCount);
        int[] leftPostOrder=Arrays.copyOfRange(postOrder,0,leftCount);
        int[] rightInOrder=Arrays.copyOfRange(inOrder,leftCount+1,inOrder.length);
        int[] rightPostOrder=Arrays.copyOfRange(postOrder,leftCount,postOrder.length-1);

        TreeNode root=new TreeNode();
        root.val=rootValue;
        root.left=buildTreeArray(leftInOrder,leftPostOrder);
        root.right=buildTreeArray(rightInOrder,rightPostOrder);

        return root;
    }
    //只给一个前序遍历，知道空的，求二叉树
    public static class ReturnValue{
        TreeNode root;
        //根节点
        int used;
        //已经用调掉的

    }
    public static ReturnValue buildTreePreorder (List<Integer> preorder) {
        if (preorder.size() == 0) {
            ReturnValue rv = new ReturnValue();
            rv.root = null;
            rv.used = 0;

            return rv;
        }
        int rootValue = preorder.get(0);
        if (rootValue == '#') {
            ReturnValue rv = new ReturnValue();
            rv.root = null;
            rv.used = 1;

            return rv;
        }
        //leftRV是左子树用掉的
        ReturnValue leftRV = buildTreePreorder(
                preorder.subList(1, preorder.size()));
        ReturnValue rightRV = buildTreePreorder(
                preorder.subList(1 + leftRV.used, preorder.size()));

        TreeNode root = new TreeNode();
        root.val = rootValue;
        root.left = leftRV.root;
        root.right = rightRV.root;

        ReturnValue rv = new ReturnValue();
        rv.root = root;
        rv.used = 1 + leftRV.used + rightRV.used;

        return rv;
    }

    public static void main(String[] args) {
        List<Integer> preorder = Arrays.asList(
                1, 2, 3, (int)'#', (int)'#', (int)'#', 4, (int)'#', 5, (int)'#', (int)'#'
        );

        ReturnValue rv = buildTreePreorder(preorder);
        //tree2str(rv.root);
        levelOrderTraversal(rv.root);
    }

    private static void preorder(StringBuilder sb, TreeNode root) {
        if (root == null) {
            return;
        }
        sb.append("(");
        sb.append(root.val);
        if (root.left == null && root.right != null) {
            sb.append("()");
        } else {
            preorder(sb, root.left);
        }
        preorder(sb, root.right);
        sb.append(")");
    }

    public static String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        preorder(sb, t);
        String r = sb.toString();
        return r.substring(1, r.length() - 1);
    }
    //层序遍历用队列，队列中不会出现null
    public static void levelOrderTraversal(TreeNode root) {
        //if (root == null) {
        //    return;
        //}

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode front = queue.poll();

            System.out.println(front.val);
            if (front.left != null) {
                queue.add(front.left);
            }
            if (front.right != null) {
                queue.add(front.right);
            }
        }
    }
}

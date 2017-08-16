// Brad Howard
// Project 2: B+ Tree

public class BPlusTree
{
   private int t;
   private BTNode root;
   private int height;
   private int numberOfNodes;
   
   public BPlusTree()
   {
      t = 2;
      root = new BTNode(1, t, true);
      numberOfNodes = 1;
      root.setNodeNumber(numberOfNodes++);
      height = 1;
   }
   
   public BPlusTree(int t)
   {
      this.t = t;
      root = new BTNode(1, t, true);
      numberOfNodes = 1;
      root.setNodeNumber(numberOfNodes++);
      height = 1;
   }
   
   public boolean find(String s)
   {
      return root.find(s);
   }
   
   public void insertWord(String s)
   {
      if(find(s))
         increasesFreq(s);
      else
         add(s);
   }
   
   private void increasesFreq(String s)
   {
      root.increasesFreq(s);
   }
   
   private void add(String s)
   {
      if(root.getData(0).equals(""))
         root.add(s);
      else
         add(root, s, null);
   }
   
   private void add(BTNode node, String s, BTNode parent)
   {
      if(node.isFull())
         if(node == root)
         {
            if(node.isLeaf())
            {
               splitRoot(s);
            }
            else
            {
               splitRoot();
               add(s);
            }
            return;
         }
         else
         {
            if(node.isLeaf())
            {
               split(node, parent, s);
            }
            else
            {
               split(node, parent);
               add(s);
            }
            return;
         }
       
      if(node.has(s))
      {
         node.increasesFreq(s);
         return;
      }
         
      for(int index = 0; index < (2 * t - 1); index++)
      {
         if(node.getData(index).equals("") || s.compareTo(node.getData(index)) < 0)
         {
            if(!node.isLeaf())
            {
               if(node.getChild(index) != null)
               {
                  add((BTNode)node.getChild(index), s, node);
                  return;
               }
               else
               {
                  node.add(s);
                  return;
               }
            }
            else
            {
               node.add(s);
               return;
            }
         }
      }
   }
   
   private void splitRoot(String s)
   {
      BTNode temp = new BTNode(0, t + 1, root.isLeaf());
      BTNode newRoot = new BTNode(1, t, false);
      BTNode leftNode;
      BTNode rightNode;
      
      for(int index = 0; index < (2 * t - 1); index++)
      {
         temp.add(root.getData(index));
         if(temp.isLeaf())
            temp.setFreq(index, root.getFreq(index).intValue());
      }
      
      if(!temp.isLeaf() && root.getChild(2 * t - 1) != null)
         temp.setEntry(root.getChild(2 * t - 1));
      
      temp.add(s);
      
      if(height == 1)
      {
         leftNode = new BTNode(2, t, true);
         rightNode = new BTNode(2, t, true);
         
         leftNode.setNodeNumber(root.getNodeNumber());
         rightNode.setNodeNumber(numberOfNodes++);
         newRoot.setNodeNumber(numberOfNodes++);
         
         for(int index = 0; index < t; index++)
         {
            leftNode.add(temp.getData(index));
            leftNode.setFreq(index, temp.getFreq(index).intValue());
         }
         for(int jdex = t; jdex < (2 * t); jdex++)
         {
            rightNode.add(temp.getData(jdex));
            rightNode.setFreq(jdex - 2, temp.getFreq(jdex).intValue());
         }
         
         newRoot.add(rightNode.getData(0));
         leftNode.setEntry(rightNode);
         newRoot.setEntry(leftNode);
         newRoot.setEntry(rightNode);
         root = newRoot;
         height++;
         resetTiers();
      }
   }
   
   private void split(BTNode node, BTNode parent, String s)
   {
      BTNode temp = new BTNode(0, t + 1, node.isLeaf());
      BTNode leftNode;
      BTNode rightNode;
      
      for(int index = 0; index < (2 * t - 1); index++)
      {
         temp.add(node.getData(index));
         if(temp.isLeaf())
            temp.setFreq(index, node.getFreq(index).intValue());
      }
      
      temp.add(s);
      
      if(node.isLeaf())
      {
         leftNode = new BTNode(node.getTier(), t, true);
         rightNode = new BTNode(node.getTier(), t, true);
         
         leftNode.setNodeNumber(numberOfNodes++);
         rightNode.setNodeNumber(numberOfNodes++);
         
         for(int index = 0; index < t; index++)
         {
            leftNode.add(temp.getData(index));
            leftNode.setFreq(index, temp.getFreq(index).intValue());
         }
         for(int jdex = t; jdex < (2 * t); jdex++)
         {
            rightNode.add(temp.getData(jdex));
            rightNode.setFreq(jdex - 2, temp.getFreq(jdex).intValue());
         }
         
         parent.add(rightNode.getData(0));
         leftNode.setEntry(rightNode);
         if(node.itemSet.getNextSet() != null)
            rightNode.setEntry(node.itemSet.getNextSet());
         if(root.findPrevNode(node.getID()) != null)
            root.findPrevNode(node.getID()).setEntry(leftNode);
         parent.setEntry(leftNode);
         parent.setEntry(rightNode);
         resetTiers();
      }   
   }
   
   private void splitRoot()
   {
      BTNode newRoot = new BTNode(1, t, false);
      BTNode leftNode;
      BTNode rightNode;
   
      newRoot.add(root.getData(t - 1));
   
      leftNode = new BTNode(2, t, false);
      rightNode = new BTNode(2, t, false);
      
      leftNode.setNodeNumber(root.getNodeNumber());
      rightNode.setNodeNumber(numberOfNodes++);
      newRoot.setNodeNumber(numberOfNodes++);
      
      for(int index = 0; index < (t - 1); index++)
      {
         leftNode.add(root.getData(index));
         leftNode.setEntry(root.getChild(index));
      }
      leftNode.setEntry(root.getChild(t - 1));
      
      for(int jdex = t; jdex < (2 * t - 1); jdex++)
      {
         rightNode.add(root.getData(jdex));
         rightNode.setEntry(root.getChild(jdex));
      }
      rightNode.setEntry(root.getChild(2 * t - 1));
      
      newRoot.setEntry(leftNode);
      newRoot.setEntry(rightNode);
      root = newRoot;
      height++;
      resetTiers();
   }

   private void split(BTNode node, BTNode parent)
   {
      BTNode leftNode;
      BTNode rightNode;
   
      parent.add(node.getData(t - 1));
   
      leftNode = new BTNode(node.getTier(), t, false);
      rightNode = new BTNode(node.getTier(), t, false);
      
      leftNode.setNodeNumber(numberOfNodes++);
      rightNode.setNodeNumber(numberOfNodes++);
      
      // loops to split a non-leaf node
      for(int index = 0; index < (t - 1); index++)
      {
         leftNode.add(node.getData(index));
         leftNode.setEntry(node.getChild(index));
      }
      leftNode.setEntry(node.getChild(t - 1));
      
      for(int jdex = t; jdex < (2 * t - 1); jdex++)
      {
         rightNode.add(node.getData(jdex));
         rightNode.setEntry(node.getChild(jdex));
      }
      rightNode.setEntry(node.getChild(2 * t - 1));
      
      parent.setEntry(leftNode);
      parent.setEntry(rightNode);
      resetTiers();
   }
   
   private void resetTiers()
   {
      root.resetTiers();
   }
   
   public void printAllStrings()
   {
      root.printAllData();
   }
   
   public void printAllEntries()
   {
      root.printAll();
   }
   
   public void printNode(int ID)
   {
      if(!root.printNode(ID))
         System.out.println("Node not found");
   }
   
   public void search(String s)
   {
      if(!root.printEntry(s))
         System.out.println("String not found");
   }
   
   public void rangeSearch(String first, String last)
   {
      root.printRange(first, last);
   }
}
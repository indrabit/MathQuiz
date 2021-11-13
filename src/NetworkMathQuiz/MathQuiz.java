package NetworkMathQuiz;
public class MathQuiz implements Comparable<MathQuiz>{
     // private data fields
        private int Left_op;
        private String operator;
        private int Right_op;
        private int answer;
    // constructor method
    public MathQuiz (int lop,String op, int rop, int ans)
    {
        this.Left_op = lop;
        this.operator = op;
        this.Right_op = rop;
        this.answer=ans;
    }
     @Override
     public String toString()
    {
        return answer+"("+Left_op+" "+operator+" "+Right_op+")";       
    }
     // compareTo() method
        // used to compare other Dog instances (age data only)
        // this is important when using Collections.sort() method for this class
    public int compareTo (MathQuiz anotherMathQuiz)
    {   
        if (this.answer == anotherMathQuiz.answer)
        {
            return 0;
        }
        else if (this.answer < anotherMathQuiz.answer)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

    public int getLeft_op() {
        return Left_op;
    }

    public void setLeft_op(int Left_op) {
        this.Left_op = Left_op;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getRight_op() {
        return Right_op;
    }

    public void setRight_op(int Right_op) {
        this.Right_op = Right_op;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
    
}

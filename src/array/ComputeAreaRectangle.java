package array;

public class ComputeAreaRectangle {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int dax = ax2-ax1;
        int day = ay2-ay1;
        int area1=dax*day;

        int dbx = bx2-bx1;
        int dby = by2-by1;
        int area2=dbx*dby;

        // overlap
        int left = Math.max(ax1,bx1);
        int right = Math.min(ax2,bx2);
        int top = Math.min(ay2,by2);
        int bottom = Math.max(ay1,by1);

        int overlap=0;
        if(top>bottom && right>left) {
            overlap = (top-bottom)*(right-left);
        }

        return area1+area2-overlap;
    }
}

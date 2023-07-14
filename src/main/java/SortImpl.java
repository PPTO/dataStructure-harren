/**
 * 升序排列
 */
public class SortImpl {
    /**
     *简单选择排序
     */
    public int[] SimpleSelectSort(int[] array){
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j <array.length ; j++) {
                min = array[min] <= array[j] ? min : j;
            }
            int tmp = array[min];
            array[min] = array[i];
            array[i] = tmp;
        }
        return array;
    }

    /**
     * 简单插入排序
     */
    public int[] SimpleInsertSort(int[] array){
        for (int i = 1; i < array.length; i++) {
            int tmp = array[i];
            int j;
            for (j = i-1; j >= 0; j--) {
                if (array[j] > tmp){
                    array[j + 1] = array[j];
                }
                else {
                    break;
                }
            }
            array[j + 1] = tmp;
        }
        return array;
    }

    /**
     * 冒泡算法
     */
    public int[] BubbleSort(int[] array){
        for (int i = array.length-1; i > 0 ; i--){
            int flag = 0;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    flag = 1;
                }
            }
            if (flag == 0 )
                break;
        }
        return array;
    }

    /**
     * 快速排序
     * @param l 起始位置
     * @param r 结束位置
     */
    public int[] QuickSort(int[] array,int i, int j){
        if (i >= j){
            return null;
        }
        int tmp = array[i], l = i, r = j;
        while (l < r){
            while (l < r && array[r] >= tmp){
                r--;
            }
            if (l < r){
                array[l++] = array[r];
            }
            while (l < r && array[l] >tmp){
                l++;
            }
            if (l < r){
                array[r--] = array[l];
            }
        }
        array[l] = tmp;
        QuickSort(array, i, l-1);
        QuickSort(array, l, j);
        return array;
    }



    public static void main(String[] args) {
        SortImpl sort = new SortImpl();
        int[] array={10,3,8,2,5,7,1,10,3,2};
//      sort.SimpleSelectSort(array);
//      sort.SimpleInsertSort(array);
//        sort.BubbleSort(array);
        sort.QuickSort(array,0, array.length-1);
    }



}

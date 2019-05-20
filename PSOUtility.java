package pso;
//class to first min pos on a list
public class PSOUtility {
	public static int getMinPos(double[]list){
		int pos = 0;
		double minValue = list[0];
		
		for(int i=0; i<list.length;i++){
			if(list[i]<minValue){
				pos=i;
				minValue = list[i];
			}
		}
		return pos;
	}

}

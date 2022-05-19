package atTang;
/**
 * 
 * @Description
 * 该类是各种截面类的父类
 * @author Tangel
 * @date 2022年5月11日下午2:30:30
 */
public class Surface {
	//截面总面积
	double area = 0;
	//截面的面积矩
	double Sx = 0;
	//截面对X轴的惯性矩
	double Ix = 0;
	//截面对形心的惯性矩
	double Ic = 0;
	//截面形心到x轴的距离
	double yx = 0;
}


package Math;

/**
 *
 * @author Edwin
 */

public class NormalizedConvolution{
 
  protected double[] filter;  
  private double[][] normalizedFilterArray;

  public NormalizedConvolution(double[] filter) throws IllegalArgumentException
  {
    if(filter == null)
      throw new IllegalArgumentException("the given filter must not be a null value;");

    if(filter.length % 2 != 1 || filter.length < 3)
      throw new IllegalArgumentException("the given filter must have an odd length greater or equal to 3;");
    
    this.filter = filter;
     
    this.normalizedFilterArray = getNormalizedFilterArray(filter);
  }

  public Matrix convolute(Matrix A, boolean firstDimension)
  {
    double[][] data;
    Matrix B;    
    if(firstDimension)
    {
      data = A.getArrayCopy();      
      for (int i = 0; i < data.length; i++)
        data[i] = computeConvolution(data[i], normalizedFilterArray);

      B = new Matrix(data);
    }
    else
    {
      B = A.transpose();
      data = B.getArray();      
      for (int i = 0; i < data.length; i++)
        data[i] = computeConvolution(data[i], normalizedFilterArray);

      B = new Matrix(data);
      B = B.transpose();
    }
    return B;
  }

  private double[] computeConvolution(double[] data, double[][] filterArray)
  {
    double[] output = new double[data.length];
    double sum = 0;
    int halfFilter = filterArray.length/2;    
    for(int i = 0; i < data.length; i++)
    {
      sum = 0;
      if(i < halfFilter)
      {        
        for(int j = 0; j < filterArray[i].length; j++)
          sum += filterArray[i][j] * data[j];
      }
      else if(i >= (data.length - halfFilter))
      {
        //for the last elements use some truncated filters
        int filterArrayIndex = filterArray.length - (data.length - i);
        for(int j = 0; j < filterArray[filterArrayIndex].length; j++)
          sum += filterArray[filterArrayIndex][j] * data[i + j - halfFilter];
      }
      else
      {        
        int filterArrayIndex = halfFilter;
        for(int j = 0; j < filterArray[filterArrayIndex].length; j++)
          sum += filterArray[filterArrayIndex][j] * data[i + j - halfFilter];
      }

      output[i] = sum;
    }

    return output;
  }


  private double[][] getNormalizedFilterArray(double[] filter)
  {
    double[][] array = new double[filter.length][];

    int filterCount = 0;

    //computes all possible truncated versions of the filter
    for(int start = filter.length / 2; start > - filter.length / 2 - 1; start--)
    {
      array[filterCount] = new double[filter.length - Math.abs(start)];
      for (int i = 0, indexCount = 0; i + start < filter.length && i < filter.length; i++)
      {
        if(start + i >= 0)
        {
          array[filterCount][indexCount] = filter[start + i];
          indexCount++;
        }
      }

      //normalize filter
      normalizeFilter(array[filterCount]);
      filterCount++;
    }

    return array;
  }

  private void normalizeFilter(double[] filter)
  {
    double sum = 0;

    //compute filter sum
    for(int i = 0; i < filter.length; i++)
      sum += filter[i];

    //normalize
    for(int i = 0; i < filter.length; i++)
      filter[i]/=sum;
  }
  
}

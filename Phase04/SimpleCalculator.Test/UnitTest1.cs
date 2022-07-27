using SimpleCalculator.Business;
using SimpleCalculator.Business.Enums;

namespace SimpleCalculator.Test;
public class UnitTest1
{

    [Theory]
    [InlineData(2, 3, SumOperator, 5)]
    public void Calculate(int first, int second, OperatorEnum operatorType, int expected)
    {
        //Arrange
        Calculator calculator = new Calculator();
        //Act
        int result = calculator.Calculate(first, second, operatorType);
        //Assert
        Assert.Equal(result, expected);
    }
}

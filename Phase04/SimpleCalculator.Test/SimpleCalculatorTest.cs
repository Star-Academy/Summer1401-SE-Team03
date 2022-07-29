using SimpleCalculator.Business;
using SimpleCalculator.Business.Enums;

namespace SimpleCalculator.Test;

public class SimpleCalculatorTest
{
    [Theory]
    [InlineData(3, 4, OperatorEnum.sum, 7)]
    [InlineData(3, 4, OperatorEnum.sub, -1)]
    [InlineData(3, 4, OperatorEnum.division, 0)]
    [InlineData(3, 4, OperatorEnum.multiply, 12)]
    public void CalculateTest(int first, int second, OperatorEnum operatorEnum, int expected)
    {
        //Arrange
        var calculator = new Calculator();
        
        //Act
        var result = calculator.Calculate(first, second, operatorEnum);
        
        //Assert
        Assert.Equal(expected, result);
    }
}
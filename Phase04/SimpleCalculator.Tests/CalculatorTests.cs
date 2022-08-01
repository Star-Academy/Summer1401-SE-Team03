using SimpleCalculator.Business;
using SimpleCalculator.Business.Enums;

namespace SimpleCalculator.Tests;

public class CalculatorUnitTest
{
    private Calculator _calculator = new Calculator();

    [Theory]
    [InlineData(0, 2, 2)]
    [InlineData(34, 23, 57)]
    [InlineData(-2, -2, -4)]
    [InlineData(-2000, 1000, -1000)]
    public void SumCalculation_Equality_Test(int first, int second, int answer)
    {
        Assert.Equal(_calculator.Calculate(first, second, OperatorEnum.sum), answer);
    }

    [Theory]
    [InlineData(0, 2, -2)]
    [InlineData(98 , 33, 65)]
    [InlineData(98, 101, -3)]
    [InlineData(-2, -2, 0)]
    [InlineData(-2000, 1000, -3000)]
    public void SubCalculation_Equality_Test(int first, int second, int answer)
    {
        Assert.Equal(_calculator.Calculate(first, second, OperatorEnum.sub), answer);
    }

    [Theory]
    [InlineData(0, 2, 0)]
    [InlineData(78, 43, 3354)]
    [InlineData(-2, -2, 4)]
    [InlineData(-2000, 1000, -2000000)]
    public void MultiplyCalculation_Equality_Test(int first, int second, int answer)
    {
        Assert.Equal(_calculator.Calculate(first, second, OperatorEnum.multiply), answer);
    }

    [Theory]
    [InlineData(-2, -2, 1)]
    [InlineData(234, 100, 2)]
    [InlineData(-234, 100, -2)]
    [InlineData(-2000, 1000, -2)]
    public void DivisionCalculation_Equality_Test(int first, int second, int answer)
    {
        Assert.Equal(_calculator.Calculate(first, second, OperatorEnum.division), answer);
    }

    [Fact]
    public void DivisionByZero_ExceptionHandling_Test()
    {
        Action act = () => _calculator.Calculate(10, 0, OperatorEnum.division), answer;
        Assert.Throws<DivideByZeroException>(act);
    }
}
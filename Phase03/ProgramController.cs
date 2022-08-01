using static Phase03.Configs;

namespace Phase03;
public class ProgramController
{
    private static List<Student> _students;
    private static List<Grade> _grades;

    public void Run()
    {
        LoadData();
        SetAverageScores();
        PrintResult(GetSortedAverages());
    }
    
    private void LoadData()
    {
        JsonLoader jsonLoader = new JsonLoader();
        _students = jsonLoader.Load<List<Student>>(PathToStudentsJson);
        _grades = jsonLoader.Load<List<Grade>>(PathToGradesJson);
    }

    private void SetAverageScores()
    {
        _students.ForEach(student => student.Average = _grades.Where(
            grade => grade.StudentNumber == student.StudentNumber).Average(grade => grade.Score));
    }

    private List<Student> GetSortedAverages()
    {
        return _students.OrderByDescending(student => student.Average).Take(3).ToList();
    }

    private void PrintResult(List<Student> sortedStudents)
    {
        sortedStudents.ForEach(student => Console.WriteLine(student.ToString()));
    }
}
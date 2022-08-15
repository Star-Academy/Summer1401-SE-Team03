using Microsoft.AspNetCore.Mvc;
namespace Phase09;

[ApiController]
[Route("[controller]/[Action]")]
public class SimpleController : ControllerBase
{
    [HttpGet]
    public string Get()
    {
        return "Hello world!";
    }
}
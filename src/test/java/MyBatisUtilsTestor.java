import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

public class MyBatisUtilsTestor {
    @Test
    public void testCase1(){
        String  result =(String) MyBatisUtils.executeQuery(sqlSession -> {
            String  out =(String)sqlSession.selectOne("test.sample");
            return out;
        });
        System.out.println(result);
    }
}

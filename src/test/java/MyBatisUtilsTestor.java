import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

public class MyBatisUtilsTestor {
    @Test
    public void testCase1(){
        MyBatisUtils.executeQuery(sqlSession -> {
            String  out =(String)sqlSession.selectOne("test.sample");
            return out;
        });
    }
}

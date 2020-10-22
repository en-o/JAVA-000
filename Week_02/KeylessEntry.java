import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author tn
 * @version 1
 * @ClassName KeylessEntry
 * @description 测试 内存泄漏   死循环
 * @date 2020/10/22 21:49
 */
public class KeylessEntry {

    static class Key{
        Integer id;

        public Key(Integer id) {
            this.id = id;
        }

        /**
         * 重写eq 处理内存泄漏
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            boolean response = false;
            if(o instanceof Key){
                response = (((Key)o).id).equals(this.id);
            }
            return response;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public static void main(String[] args) {
        Map m = new HashMap();
        while (true){
            for (int i = 0; i < 10000; i++) {
                if(!m.containsKey(new Key(i))){
                    m.put(new Key(i),"Number:"+i);
                }
            }
            System.out.println("m.size()="+m.size());
        }
    }

}

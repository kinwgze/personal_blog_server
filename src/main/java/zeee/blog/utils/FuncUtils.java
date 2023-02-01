package zeee.blog.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @Author zeeew
 * @Date 2023/2/1 19:45
 * @Description 封装后的功能
 */
public class FuncUtils {

    private FuncUtils() {};

    /**
     * 实体类对象的列表，根据单一字段排序
     * 使用排序的方式是上面的混合字符串排序的方式
     * 排序的字段必须在实体类中定位相应的getter方法，如model.getXxx()
     *
     * @param list 待排序的list
     * @param sortDir 排序方式
     * @param sortField 排序字段，
     *                  如字段是xxx，该字段必须在实体类中定义相应的getter方法，否则将抛出异常
     * @param <T> 泛型
     * @throws NoSuchMethodException 异常
     */
    public static <T> void sortByField(List<T> list, SortUtil.SortDir sortDir, String sortField) throws NoSuchMethodException {
        if (CollectionUtils.isEmpty(list) || Objects.isNull(sortDir) || Objects.equals(SortUtil.SortDir.None, sortDir)
                || StringUtils.isEmpty(sortField)) {
            return;
        }
        T ele = list.get(0);
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) ele.getClass();
        list.sort(createComparator(clazz, sortDir, sortField));
    }

    /**
     * 创建某类对象的单字段排序比较器，用于排序
     * @param clazz 对象的class
     * @param sortDir 排序方式
     * @param sortField 排序字段
     * @return 比较器
     * @param <T> 参与排序的对象类型
     */
    private static <T> Comparator<T> createComparator(final Class<T> clazz, final SortUtil.SortDir sortDir
            , final String sortField) throws NoSuchMethodException {
        return new Comparator<T>() {

            /**
             * 获取field字段值得方法
             */
            private final Method fieldValueMethod;
            private final SortUtil sortUtil = new SortUtil();

            /**
             * 正负号算子，默认是1
             * 当sortDir=Desc时，sign=-1，用于compare方法最后先按照顺序比较的值，再乘以该算子
             */
            private int sign = 1;

            /**
             * 初始化代码块，相当于构造函数
             */
            {
                if (Objects.isNull(clazz) || StringUtils.isEmpty(sortField) || Objects.isNull(sortDir)
                        || Objects.equals(SortUtil.SortDir.None, sortDir)) {
                    fieldValueMethod = null;
                } else {
                    // 将propertyName首字母大写，转换为getter方法名，如name -> getName
                    String methodName = "get" + sortField.substring(0, 1).toUpperCase() +
                            (sortField.length() <= 1 ? "" : sortField.substring(1));

                    // 有可能找不到该get方法，抛出异常
                    fieldValueMethod = clazz.getMethod(methodName);

                    // 如果是逆序，则翻转顺序
                    if (Objects.equals(sortDir, SortUtil.SortDir.Desc)) {
                        sign = -1;
                    }
                }

            }

            @Override
            public int compare(T o1, T o2) {
                if (Objects.isNull(fieldValueMethod)) {
                    return 0;
                }
                int ret;
                try {
                    Object val1 = fieldValueMethod.invoke(o1);
                    Object val2 = fieldValueMethod.invoke(o2);

                    ret = sortUtil.compare(val1, val2);
                    ret = sign * ret;

                } catch (InvocationTargetException | IllegalAccessException e) {
                    ret = 0;
                }
                return ret;
            }
        };
    }
}

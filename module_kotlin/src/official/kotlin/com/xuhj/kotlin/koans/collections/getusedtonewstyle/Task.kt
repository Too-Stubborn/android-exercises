package com.xuhj.kotlin.koans.collections.getusedtonewstyle

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {

}
//public Collection<String> doSomethingStrangeWithCollection(
//Collection<String> collection
//) {
//    Map<Integer, List<String>> groupsByLength = Maps.newHashMap();
//    for (String s : collection) {
//        List<String> strings = groupsByLength.get(s.length());
//        if (strings == null) {
//            strings = Lists.newArrayList();
//            groupsByLength.put(s.length(), strings);
//        }
//        strings.add(s);
//    }
//
//    int maximumSizeOfGroup = 0;
//    for (List<String> group : groupsByLength.values()) {
//        if (group.size() > maximumSizeOfGroup) {
//            maximumSizeOfGroup = group.size();
//        }
//    }
//
//    for (List<String> group : groupsByLength.values()) {
//        if (group.size() == maximumSizeOfGroup) {
//            return group;
//        }
//    }
//    return null;
//}

fun doSomethingStrangeWithCollection(collection: Collection<String>): Collection<String>? {

    val groupsByLength = collection.groupBy { it.length }

    val maximumSizeOfGroup = groupsByLength.values.map { it.size }.max()

    return groupsByLength.values.firstOrNull { it.size == maximumSizeOfGroup }
}

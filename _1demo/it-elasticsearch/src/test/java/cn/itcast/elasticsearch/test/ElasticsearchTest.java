package cn.itcast.elasticsearch.test;

import cn.itcast.elasticsearch.ItemRepository;
import cn.itcast.elasticsearch.pojo.Item;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public  void testIndex(){
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testCreate(){
        Item item = new Item(1L, "小米手机8", " 手机",
                "小米", 3599.00, "http://image.leyou.com/13123.jpg");
        this.itemRepository.save(item);


        /*List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        this.itemRepository.saveAll(list);*/
    }

    @Test
    public void testFind(){
        Iterable<Item> items = this.itemRepository.findAll(Sort.by("price").descending());
        items.forEach(System.out::println);
    }

    @Test
    public void testFindByTitle(){
        List<Item> items = this.itemRepository.findByPriceBetween(3699d, 4499d);

//        List<Item> items = this.itemRepository.findByTitle("手机");
        items.forEach(System.out::println);
    }


    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    //通过查询构建器工具构建查询条件
    @Test
    public void  testSearch(){
        //通过查询构建器工具构建查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "手机");
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
    items.forEach(System.out::println);
    }

    //自定义构建器
    @Test
    public void testNative(){
        //自定义构建起
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));
        //查询分页获取分页结果集
        Page<Item> itemPage = this.itemRepository.search(queryBuilder.build());
    System.out.println(itemPage.getTotalPages());
    System.out.println(itemPage.getTotalElements());
    itemPage.getContent().forEach(System.out::println);

    }


    //分页查询
    @Test
    public void testPage(){
        //分页排序
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"));
        //添加分页条件 页码是从0开始的
        //queryBuilder.withPageable(PageRequest.of(1,2));
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //查询分页获取分页结果集
        Page<Item> itemPage = this.itemRepository.search(queryBuilder.build());
        System.out.println(itemPage.getTotalPages());
        System.out.println(itemPage.getTotalElements());
        itemPage.getContent().forEach(System.out::println);
    }

    //聚合查询
    @Test
    public void  testAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合查询 强转为接口
        AggregatedPage<Item> itemPage =(AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        //返回聚合结果集 根据聚合类型和字段类型强转化为子类  brand-StringTerms
        StringTerms brandAgg =(StringTerms)itemPage.getAggregation("brandAgg");
        //通过getBucket获取桶集合
        List<StringTerms.Bucket> buckets=brandAgg.getBuckets();
    buckets.forEach(
        bucket -> {
          System.out.println(bucket.getKeyAsString());
          System.out.println(bucket.getDocCount());
        });
    }


    //聚合查询 桶内度量 子聚合
    @Test
    public void  testSubAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand")
                .subAggregation(AggregationBuilders.avg("price_avg").field("price")));

        //结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合查询 强转为接口
        AggregatedPage<Item> itemPage =(AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        //返回聚合结果集 根据聚合类型和字段类型强转化为子类  brand-StringTerms
        StringTerms brandAgg =(StringTerms)itemPage.getAggregation("brandAgg");
        //通过getBucket获取桶集合
        List<StringTerms.Bucket> buckets=brandAgg.getBuckets();
    buckets.forEach(
        bucket -> {
          System.out.println(bucket.getKeyAsString());
          System.out.println(bucket.getDocCount());
          // 获取子聚合的map集合  key聚合名称 value对应子聚合对象
          Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
          InternalAvg price_avg = (InternalAvg) stringAggregationMap.get("price_avg");
          System.out.println(price_avg.getValue());
        });
    }
}






















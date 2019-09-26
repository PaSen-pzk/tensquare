package com.tensuqare.base.service;

import antlr.StringUtils;
import com.tensquare.util.IdWorker;
import com.tensuqare.base.dao.LabelDao;
import com.tensuqare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label){
        Specification<Label> specificatioon = createSpecificatioon(label);
        return labelDao.findAll(specificatioon);
    }
    /**
     * 构建查询条件
     * @param label
     * @return
     */
    private Specification<Label> createSpecificatioon(Label label){
        return new Specification<Label>() {
            /**
             * @param root  跟对象，也就是要把条件封装到那个对象中。where 类名=label。getId
             * @param query  封装的都是查询关键字，比如 group by    order by 等
             * @param cb    用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    predicate.add(cb.like(root.get("labelname").as(String.class),
                            "%"+label.getLabelname()+"%"));  // where labelname like "%小明%"；
                }
                if(label.getState()!= null && !"".equals(label.getState())){
                    predicate.add(cb.equal(root.get("state").as(String.class),label.getState()));
                }
                if(label.getRecommend()!=null && !"".equals(label.getRecommend())){
                    predicate.add(cb.like(root.get("recommend").as(String.class),"%"+label.getRecommend()+"%"));
                }
                if(label.getFans()!=null && !"".equals(label.getFans())){
                    predicate.add(cb.like(root.get("fans").as(String.class),"%"+label.getFans()+"%"));
                }
                return cb.and(predicate.toArray(new Predicate[predicate.size()]));       }
        };
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root  跟对象，也就是要把条件封装到那个对象中。where 类名=label。getId
             * @param query  封装的都是查询关键字，比如 group by    order by 等
             * @param cb    用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    predicate.add(cb.like(root.get("labelname").as(String.class),
                            "%"+label.getLabelname()+"%"));  // where labelname like "%小明%"；
                }
                if(label.getState()!= null && !"".equals(label.getState())){
                    predicate.add(cb.equal(root.get("state").as(String.class),label.getState()));
                }
                if(label.getRecommend()!=null && !"".equals(label.getRecommend())){
                    predicate.add(cb.like(root.get("recommend").as(String.class),"%"+label.getRecommend()+"%"));
                }
                if(label.getFans()!=null && !"".equals(label.getFans())){
                    predicate.add(cb.like(root.get("fans").as(String.class),"%"+label.getFans()+"%"));
                }
                return cb.and(predicate.toArray(new Predicate[predicate.size()]));
            }
        },pageable);
    }
}

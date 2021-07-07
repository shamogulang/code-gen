package %s;

import %s;
import %s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class %sRepository {

    @Autowired
    private %sMapper %sMapper;

    public int insertBatch(List<%s> %sList){

        return mngUserMapper.batchInsert(%sList);
    }

    private int insertBatchSelective(List<%s> %sList){

        return mngUserMapper.batchInsertSelective(%sList);
    }
}
package jpabook.jpashop.service;

import java.util.List;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

//    @Transactional
//    public Item updateItem(Long itemId, Book bookParam) {
//        Item findItem = itemRepository.findOne(itemId); //영속상태 엔티티
//        findItem.setPrice(bookParam.getPrice());//이렇게 set으로 처리하는 것도 별로임. addStock()처럼 의미있는 메소드 만들어서 변경 지점을 엔티티로 가도록 설계하기
//        findItem.setName(bookParam.getName());
//        findItem.setStockQuantity(bookParam.getStockQuantity());
//        //@Transactional에 의해 커밋됨 - JPA flush됨 - 변경된 친구 찾음 - 업데이트쿼리 날림:변경감지
//        return findItem;
//    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { //여기 파라미터가 너무 많아지면 UpdateItemDto 생성해서 넣는다
        Item findItem = itemRepository.findOne(itemId); //트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고,
        findItem.setName(name); //엔티티의 데이터를 직접 변경하세요
        findItem.setPrice(price); //findItem.change(name, price, stockQuantity); //이렇게 메소드 만들어놓는게 추적하기에 좋음!
        findItem.setStockQuantity(stockQuantity);
        //커밋 시점에 변경 감지가 실행됩니다
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

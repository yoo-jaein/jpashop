package jpabook.jpashop.controller;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book(); //createBook 생성 메서드 하는게 더 좋음
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

//        Book book = new Book(); //객체는 새로 생성되는데 식별자가 있는 상태.. 이미 DB에 저장되었던 데이터. 준영속 상태의 엔티티 -> JPA가 관리하지 않는 상태
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn()); //그리고 컨트롤러에서 엔티티를 괜히 만들지 말자

        //1. 변경 감지 기능 사용. itemService.updateItem() 참고
        //2. 병합(머지) 사용. em.merge() 참고. 준영속 엔티티를 영속 엔티티로 바꿔줌. 파라미터로 넘겨준 애는 영속 엔티티가 안됨.. 영속 엔티티를 새로 반환하는것.. 주의!
        //변경 감지는 원하는 속성만 변경 가능하지만, 병합은 아예 모든 속성이 변경되기 때문에 파라미터에 값이 없으면 null이 되어버려서 주의해야 함.
        //엔티티를 변경할 때는 한땀 한땀 변경 감지를 쓰자
        //itemService.saveItem(book);

        //더 나은 설계
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());


        return "redirect:/items";
    }
}

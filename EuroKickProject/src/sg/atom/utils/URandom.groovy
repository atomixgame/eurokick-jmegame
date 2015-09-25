package sg.games.football.gameplay.info

import java.util.Random;
/**
 *
 * @author hungcuong
 * Make random from a set of exits values like Enum or a List
 * Groovy style!
 */
public class URandom {
    private static final Random RND = new Random();
    private static URandom _self;
    
    private URandom(){
        _self = this;
    }
    
    public static def random(def values){
        if (values instanceof Enum){
            def list = values.getEnumConstants()
            return random(list)
        } else if (values instanceof List){
            return values[RND.nextInt(values.size())];
        }else {
         
        }
    }
    public static URandom getDefault(){
        if (_self==null){
            _self = new URandom();
        }
        return _self;
    }
    public static Random getRandom(){
        return RND;
    }
    
    public static rInt(i1,i2){
        return i1 + RND.nextInt(i2 - i1);
    }
    
    public static def rList(values){
        return values[RND.nextInt(values.size())];
    }
}

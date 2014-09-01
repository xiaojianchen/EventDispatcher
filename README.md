EventDispatcher
===============

一个LinearLayout里面有一个button，为什么点击事件可以直接传给button，因为这是linearlayout的代码里面自己控制的，事件传递并非有什么默认值，有的控件可以往下传递，有的控件不

1、面向对象的思想 、简洁的代码
2、onI 和 onT的调用 时间
3、return true和return super.on... 的区别
4、在onI和onT两个方法里面实现的区别 和 方案。
5、scrollview滚动的赋值，为什么调用了super.on 和 return true并没有效果，具体为什么要看父类的实现
6、listview定长怎样做 ？ 不定长怎样做？
7、为什么手指一滑会超越这个位置，而慢慢滑动就可以？

OnInterceptTouchEvent只在刚刚触摸屏幕的时间，才会触发，在触发了OnI之后 ，再当你的手指在屏幕上滑动的时间，只会触发onTouchEvent,而且如果你松手再滑动，也是触发的onTouchEvent,那么这个时间你在onIntercetTouchEvent里面的判断全部都没有用了，那么它到临界点之后就还会继续滑动，此时你要设定listview的高度，让它就那么高，否则一定会滑出屏幕，如果不设置高度呢，你关于判断临界的逻辑就不能在onInterceptTouchEvent里面，就要在onTocheEvent里面也就是说在这里面你要及时改变它的事件分发。所以一定要了解，这两个方法何时会被调用，一个只在开始调用，一个在后续滑动及松手后会被调用，但是你最好只在一个里面做逻辑。

在OnInterceptTouchEvent里面做逻辑有个缺点就是只能在刚刚接触到屏幕的时间触发到事件，在后续里面全部不能触发事件，这个其实就要求你要在开始的时间，给一些控件设置好高度，否则会直接滑过去，但是如果设置高度的话它会到那里直接停留在那边，如果不想在OnInterceptTouchEvent里面做逻辑，想在onTouchEvent里面做，那么你就要在这里面判断这些个逻辑，可以你在move的过程中就直接改变了它的事件传递。

在onI 方法里面返回了true，不再往下传递，让自身去处理，但是为何不起作用，其实，最重要的是要看父类的实现，因为在onI 方法里面有大量的数据需要初始化，而此时你并没有调用这个方法，

return true/return super.onI 就是自己拦截，去执行自身的onTouchEvent方法，return true一定会执行自身的onTouchEvent方法，但是，如果单纯的在某个action_move方法里面调用return super.onI 有可能并不会调用onTouchEvent方法，因为你只在某个事件下调用了这个方法，有可能这个事件，并不能把要执行下面的任务的数据给全部初始化出来，所以可能不调用，此时，你应该去看看父类的源码 ，到底在哪些事件里面会初始化相关的数据，那么 你要在这些个事件里面全部调用一下super.onI ,那么这么调用会不会出现问题，还是看源码，看看会造成什么影响 。

它在滑动过程中之所以能够停下来就是因为你设置了listview的高度，它只能这么高，如果不设置会出现什么样的情况呢？它会一直滑过去。如果想要不设置高度来做就要在onTouchEvent里面做了。

return true 一律是拦截，不再往下传递，自身消化这个事件，如果是return false，一律往下传。


事件传递没有我之前相像中的默认一说，我以为是默认往下传，事实并不是，我当时可能只看到ViewGroup里面的onInterceptTouchEvent只返回了一个return false，但是所有继承它的控件并不这样做的它们是根据自身的情况做自己的拦截逻辑的，不同的控件是不一样的。


ScrollView里面三个重要的滚动方法：
     computeVerticalScrollOffset：开始是0，以左上角为坐标，表示滚动了多远，在这个方法里面动态获取它的滚动值，而不是在onScroll里面，在这里获取会比在onScroll里面获取更好。
     computeVerticalScrollExtent：目前两个屏幕所呈现出现总的的可滚动空间的大小 
     computeVerticalScrollRange：整个scrollView的高度。

在onTouchEvent里面的actionDown的事件里面其实是得不到用户刚刚点击的坐标的，你只能在onInterceptTouchEvent里面得到。

总结：
     1、了解在两个方法的触发时间
     2、调用父类是否触发要看源码
     3、事件怎么传递关键还是不同组件的传递方式不同，没有默认这一说。
     4、return true return super.onI的区别
     5、return super.onI 在哪里调用也大有讲究，关键看父类的源码
     6、侦听ScrollView的滚动变化不用onScroll而是用computerVerticalScrollOffset
     7、这个只是事件的拦截，还不是事件的分发，其实只要在滚动，只要有事件传递dispather这个方法会一直执行，这个方法决定执行上面的两个方法中的哪一个。

<?php $this->load->view('template/header.php'); ?>

<body>

<nav class="navbar navbar-inverse" style="border-radius: 0px;margin-bottom: 0px">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Bookstore</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <?php if(isset($name)):?>
                    <li>
                        <a onclick="show_cart()"><span class = "glyphicon glyphicon-shopping-cart" style="font-size: 16px"></span></a>
                    </li>
                    <li><a href="#">Hello <?=$name?></a></li>
                    <li><a href="<?php echo site_url('login/logout')?>">Log out</a></li>
                <?php else:?>
                    <li><a href="<?php echo site_url('login')?>">Sign in</a></li>
                <?php endif;?>
            </ul>
        </div>
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
	   <h1>Welcome to ALPHA's BOOKSTORE</h1>
       <p style="font-family: 微软雅黑">WEB开发技术BookStore迭代一</p>
       <p style="font-family: 微软雅黑">肖煜伟 5140379066</p>
    </div>
</div>


<div class="container">

    <h1>
        Take Your Favourite Books home.
    </h1>
    <table class="table table-striped">
        <tr>    
            <th field="isbn">isbn</th>
            <th field="title">title</th>
            <th field="author">author</th>
            <th field="press">press</th>
            <th field="price">price</th>
            <th filed="sales">sales</th>
            <th> </th>
        </tr>

        <?php foreach ($books as $book_index => $row):?>
            <tr>
                <td><?=$row->isbn?></td>
                <td><?=$row->title?></td>
                <td><?=$row->author?></td>
                <td><?=$row->press?></td>
                <td><?=intval($row->price)/100?></td>
                <td><?=$row->sales?></td>
                <td>
                    <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal<?=$book_index?>">
                      Buy
                    </button>
                </td>
            </tr>
            <div class="modal fade" id="myModal<?=$book_index?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content" style="margin:0 auto;width:300px;min-width: 280px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel">How many 《<?=$row->title?>》do you want to bug</h4>
                        </div>
                        <div class="modal-body" style="width:50%;margin:0 auto">
                            <button class="btn btn-md"  onclick="minusBookNum(<?=$book_index?>)" style="margin-left:0px;">
                            <span class="glyphicon glyphicon-minus"></span></button> 
                            <span id = "amount_<?=$book_index?>" style="padding:5px;font-size: 20px">1</span>
                            <button class="btn btn-md"  onclick="addBookNum(<?=$book_index?>)" style="margin-right:0px">
                            <span class="glyphicon glyphicon-plus"></span></button> 
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="add_to_cart(<?=$row->bookID?>,<?=$book_index?>)">Bug</button>
                        </div>
                    </div>
                </div>
            </div>
        <?php endforeach;?>
    </table>
    <div id="tip"></div>



    <div class="modal fade" id="cart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">Cart</h4>
                </div>
                <div class="modal-body">
                    <table class="table table-striped">
                        <tr id="heading">    
                            <th field="isbn">isbn</th>
                            <th field="title">title</th>
                            <th field="author">author</th>
                            <th field="press">press</th>
                            <th field="price">price</th>
                            <th filed="amount">amount</th>
                            <th filed="total Price">total</th>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" id="order" onclick="order()">Order</button>
                </div>
            </div>
        </div>
    </div>

</div>


<script type="text/javascript">
    var base_url = "<?php echo site_url('store')?>/";

    function addBookNum(book_index)
    {
        amount = $('#amount_'+book_index).html();
        amount = parseInt(amount) + 1;
        $('#amount_'+book_index).html(amount);
    }

    function minusBookNum(book_index)
    {
        amount = $('#amount_'+book_index).html();
        if(amount > 1)
            amount = parseInt(amount) - 1;
        $('#amount_'+book_index).html(amount);
    }

    function add_to_cart(book_id, book_index)
    {
        $.post(
            base_url+'/add_to_cart',
            {
                'bookID':book_id,
                'amount':$('#amount_'+book_index).html()
            },
            function(msg){
                console.log(msg);
                if(msg == "success")
                {
                    $('#myModal'+book_index).modal('hide');
                    showTip('success', 'success');
                }
                else if(msg == 1)
                {
                    showTip('you have to login','danger');
                    setTimeout(function(){
                        window.location.href="<?php echo site_url('login')?>"; 
                    },1000);
                }
            });
    }


    function show_cart()
    {
        //console.log(base_url+'get_cart_info');
        $.ajax({
            url:base_url+'get_cart_info',
            dataType:'json',
            success:function(data)
            {
                //console.log(data);
                $('#cart').modal('show');
                if(data.length == 0)
                    $('#cart').find('.modal-footer').find('#order').attr('disabled','disabled');
                else
                    $('#cart').find('.modal-footer').find('#order').removeAttr('disabled');

                $modal = $('#cart').find('.modal-body').find('.table');
                $modal.find("tr[id!='heading']").remove();
                for(var i=0; i < data.length; i++)
                {
                    //console.log(data[i]);
                    $modal.append("<tr>\
                        <td>"+data[i].isbn+"</td>\
                        <td>"+data[i].title+"</td>\
                        <td>"+data[i].author+"</td>\
                        <td>"+data[i].press+"</td>\
                        <td>"+data[i].price/100+"</td>\
                        <td>"+data[i].amount+"</td>\
                        <td>"+data[i].price*data[i].amount/100+"</td>\
                        </tr>");
                }
            }
        }); 
    }

    function order()
    {
        $.ajax({
            url:base_url+'order',
            success:function(msg)
            {
                //console.log( typeof msg);
                if(msg)
                {
                    //console.log(msg);
                    $('#cart').modal('hide');
                    showTip('success', 'success');
                }
                else
                    showTip('some thing go wrong','danger');   
            }
        }); 
    }

    function showTip(tip, type) {
        var $tip = $('#tip');
        $tip.attr('class', 'alert alert-' + type).text(tip).css('margin-left', - $tip.outerWidth() / 2).fadeIn(500).delay(1000).fadeOut(500);
    }
</script>

</body>
</html>
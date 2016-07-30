<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><?php echo isset($title)?$title:"bookstore"?></title>
		<script src="<?php echo base_url('js/jquery-1.12.2.min.js');?>"></script>
		<script src="<?php echo base_url('js/bootstrap.min.js');?>"></script>
		<link href="<?php echo base_url('css/bootstrap.css');?>" rel="stylesheet">
		<link href="<?php echo base_url('css/style.css');?>" rel="stylesheet">
</head>
<body>
	<?php if (count($rows) === 0): ?>
		<h1>No order</h1>
	<?php else:?>
	<?php foreach ($rows as $order_index => $order):?> 
		<div class="container content-wrapper order">
			<ul>
				<li><strong>OrderID:</strong><?=$order->orderID?></li>
				<li><strong>UserID:</strong><?=$order->userID?></li>
				<li><strong>UserName:</strong><?=$order->name?></li>
				<li><strong>IsDeal:</strong><?php echo $order->isDeal == '0'?'Not finish':'Finished' ?></li>
	        </ul>
	        <table class="table table-striped">
        		<tr>
        			<th field="isbn" width="50">isbn</th>
        			<th field="title" width="50">title</th>
        			<th field="author" width="50">author</th>
        			<th field="press" width="50">press</th>
        			<th field="amount" width="50">amount</th>
        			<th field="unit_price" width="50">unit Price</th>
        			<th field="total_price" width="80">Price</th>
        			<th field="total_price" width="50"> </th>
        		</tr>
		       <?php foreach ($order->ordersbook as $book_index => $book):?>
		       		<tr>
		       			<td><?=$book->isbn?></td>
		       			<td><?=$book->title?></td>
		       			<td><?=$book->author?></td>
		       			<td><?=$book->press?></td>
		       			<td>
		       				<button class="btn btn-xs modify"  onclick="minusBookNum(<?=$order_index?>, <?=$book_index?>,<?=$book->price?>)">
		       				<span class="glyphicon glyphicon-minus"></span></button> 
		       				<span class = "amount"><?=$book->amount?></span>
		       				<button class="btn btn-xs modify"  onclick="addBookNum(<?=$order_index?>, <?=$book_index?>,<?=$book->price?>)">
		       				<span class="glyphicon glyphicon-plus"></span></button> 
		       			</td>
		       			<td><?=intval($book->price)/100?></td>
		       			<td class="price"><?=intval($book->price)*intval($book->amount)/100?></td>
		       			<td>
		       			<button class="btn btn-default btn-xs modify" onclick="removeBook(<?=$order_index?>, <?=$book_index?>,<?=$book->ordersBookID?>)"><span class="glyphicon glyphicon-remove"></span></button>
		       			</td>
		       		</tr>
		       <?php endforeach;?>
	       </table>
	        <p>
	            <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
	            	<strong>Order time:</strong><?=$order->orderTime?>
	        </p>
	        <button class="btn btn-default" onclick="modifyBook(<?php echo $order_index  ?>)" >Modify</button>
            <a class="btn btn-default" href="<?php echo site_url('admin/order/destroy_order/').'/'.$order->orderID?>">delete</a>
            <button class="btn btn-default modify" onclick="finishModify(<?php echo $order_index  ?>)" >Update</button>
		</div>
	<?php endforeach;?>
	<?php endif;?>
	<div id="tip"></div>

	<script type="text/javascript">
		var base_url = "<?php echo site_url('admin/order')?>";


		function removeBook(order_id, book_id, ordersBookID){
			 $.get(base_url+"/destroy_ordersbook/"+ordersBookID,function(msg){
			 	if(msg == 'true')
			 	{
			 		showTip('success', 'success');
				 	//console.log(msg);
					book_id = parseInt(book_id)+1;
					$('.order:eq('+order_id+')').find('tr:eq('+book_id+')').css('display','none');	 	
				 	//console.log(order_id, book_id, ordersBookID);

			 	}
			 	else
			 	{
			 		showTip('some thing go wrong','danger');
			 	}
			 });
			
		}

		function finishModify(order_id){
			var obj = <?php echo json_encode($rows);?>;
			obj = obj[order_id];
			var data = [];
			var tuple = $('.order:eq('+order_id+')').find('tr').first().next();
			// console.log(tuple.find('.amount'));
			//console.log(obj);
			for (var i = 0; i<obj['ordersbook'].length; i++) {
				var isbn = tuple.find('td').first().html();
				if(isbn == obj['ordersbook'][i].isbn)
				{
					data.push({
						'ordersBookID':obj['ordersbook'][i].ordersBookID,
						'bookID':obj['ordersbook'][i].bookID,
						'orderID':obj['orderID'],
						'amount':tuple.find('.amount').html()
					});
					tuple = tuple.next();
				}
			}
			$.post( base_url+'/update_order',
			 		{'ordersbook':data},
			 		function(msg){
			 			//console.log(msg);
			 			if( msg == "success")
			 			{
			 				
			 				$('.order:eq('+order_id+')').find('.modify').css({"display":"none"});
			 				showTip('success', 'success');
			 			}
			 			else
			 			{
			 				showTip('some thing go wrong','danger');
			 			}
			 	});
		}

		function modifyBook(order_id) {
			var tuple = $('.order:eq('+order_id+')').find('.modify');
			tuple.css({"display":"inline"});
		}

		function minusBookNum(order_id, book_id, price)	{
			order_id = parseInt(order_id);
			book_id = parseInt(book_id)+1;
			price = parseInt(price);
			var tuple = $('.order:eq('+order_id+')').find('tr:eq('+book_id+')');
			var amount = tuple.find('.amount');
			var num = parseInt(amount.html());
			if(num == 1)
				return;
			num -= 1;
			amount.html(num);
			tuple.find('.price').html(num*price/100);
		}

		function addBookNum(order_id, book_id, price)	{
			order_id = parseInt(order_id);
			book_id = parseInt(book_id)+1;
			price = parseInt(price);
			var tuple = $('.order:eq('+order_id+')').find('tr:eq('+book_id+')');
			var amount = tuple.find('.amount');
			var num = parseInt(amount.html());
			
			num += 1;
			amount.html(num);
			tuple.find('.price').html(num*price/100);
		}

		function showTip(tip, type) {
		    var $tip = $('#tip');
		    $tip.attr('class', 'alert alert-' + type).text(tip).css('margin-left', - $tip.outerWidth() / 2).fadeIn(500).delay(1000).fadeOut(500);
		}

	</script>

</body>
</html>
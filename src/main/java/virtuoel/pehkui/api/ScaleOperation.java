package virtuoel.pehkui.api;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import virtuoel.pehkui.mixin.OperationArgumentTypeAccessor;

public enum ScaleOperation
{
	SET((prev, now) ->
	{
		return now;
	}),
	ADD((prev, now) ->
	{
		return prev + now;
	}),
	SUBTRACT((prev, now) ->
	{
		return prev - now;
	}),
	MULTIPLY((prev, now) ->
	{
		return prev * now;
	}),
	DIVIDE((prev, now) ->
	{
		if (now == 0)
		{
			throw new ArithmeticException("Cannot divide by zero");
		}

		return prev / now;
	}),
	POWER((prev, now) ->
	{
		return (float) Math.pow(prev, now);
	});

	private final Operation operation;

	ScaleOperation(Operation operation)
	{
		this.operation = operation;
	}

	public float calculate(float scaleData, float value)
	{
		return operation.apply(scaleData, value);
	}

	public float calculateByCommand(float scaleData, float value) throws CommandSyntaxException
	{
		try
		{
			return calculate(scaleData, value);
		}
		catch (ArithmeticException e)
		{
			throw OperationArgumentTypeAccessor.getDivisionZeroException().create();
		}
	}

	public String asString()
	{
		return name().toLowerCase();
	}

	@FunctionalInterface
	public interface Operation
	{
		float apply(float scaleData, float value);
	}
}

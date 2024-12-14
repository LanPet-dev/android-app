from transformers import AutoModelForCausalLM, AutoTokenizer
import sys
import torch

def review_code(file_path):
    with open(file_path, 'r') as f:
        code = f.read()
    
    tokenizer = AutoTokenizer.from_pretrained('codellama/CodeLlama-7b-hf')
    model = AutoModelForCausalLM.from_pretrained('codellama/CodeLlama-7b-hf')
    
    prompt = f"Review this code and suggest improvements:\n\n{code}\n\nCode Review:"
    inputs = tokenizer(prompt, return_tensors="pt", max_length=2048, truncation=True)
    
    with torch.no_grad():
        outputs = model.generate(**inputs, max_length=500, temperature=0.7)
    
    review = tokenizer.decode(outputs[0], skip_special_tokens=True)
    
    with open('review_comments.txt', 'a') as f:
        f.write(f"\n## Review for {file_path}:\n{review}\n")

if __name__ == "__main__":
    for file_path in sys.argv[1:]:
        if file_path.endswith(('.py', '.js', '.java', '.cpp', '.go')):
            review_code(file_path)
